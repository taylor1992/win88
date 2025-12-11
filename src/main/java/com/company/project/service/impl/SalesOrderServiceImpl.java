package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.company.project.common.utils.Win88Util;
import com.company.project.entity.*;
import com.company.project.mapper.SalesOrderItemMapper;
import com.company.project.service.*;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.SalesOrderMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service("salesOrderService")
@AllArgsConstructor
public class SalesOrderServiceImpl extends ServiceImpl<SalesOrderMapper, SalesOrderEntity> implements SalesOrderService {

    private final SalesOrderItemService salesOrderItemService;

    private final SysDictService sysDictService;

    private final SysDictDetailService sysDictDetailService;

    private final ShopService shopService;

    private final ShopSkuService shopSkuService;

    private final SalesOrderMapper salesOrderMapper;

    private final SalesOrderItemMapper salesOrderItemMapper;

    /**
     * 解析 CSV 文件并将数据导入数据库（包含主表和明细表）
     *
     * @param file   上传的 CSV 文件
     * @param shopId 选定的店铺 ID
     * @return 导入成功的订单主表记录数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int importOrdersFromCsv(MultipartFile file, Long shopId) throws IOException, ParseException {

        // ===== 1. 准备状态字典 =====
        SysDictEntity dict = sysDictService.getOne(
                Wrappers.<SysDictEntity>lambdaQuery().eq(SysDictEntity::getName, "OrderStatus"));
        List<SysDictDetailEntity> dictList = sysDictDetailService.list(
                Wrappers.<SysDictDetailEntity>lambdaQuery().eq(SysDictDetailEntity::getDictId, dict.getId()));

        // key: label 大写, value: 数字值
        Map<String, String> statusDictMap = dictList.stream()
                .collect(Collectors.toMap(d -> d.getLabel().toUpperCase(), SysDictDetailEntity::getValue));

        ShopEntity shop = shopService.getById(shopId);

        // 店铺 SKU -> 内部 SKU ID 映射
        List<ShopSkuEntity> skuList = shopSkuService.list();
        Map<String, Long> skuMap = skuList.stream()
                .collect(Collectors.toMap(
                        s -> s.getShopId() + "_" + s.getSellerSkuCode(),
                        ShopSkuEntity::getSkuId
                ));

        try (Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)) {

            // ===== 2. CSV 基本配置 =====
            CSVFormat format = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()   // 第一行作为表头
                    .withIgnoreHeaderCase()      // 表头名大小写不敏感
                    .withTrim();                 // 去掉前后空格

            try (CSVParser csvParser = new CSVParser(reader, format)) {

                // ===== 3. 构建 header 别名映射 =====
                Map<String, String> headerAlias = new HashMap<>();

                Map<String, Integer> headerMap = csvParser.getHeaderMap();
                if (headerMap == null || headerMap.isEmpty()) {
                    throw new RuntimeException("CSV 文件缺少表头行，请确认第一行是字段名称（例如：Order ID, Order Status 等）");
                }

                for (String raw : headerMap.keySet()) {
                    String canonical = raw.replace("\uFEFF", "")   // 去掉 BOM
                            .trim()
                            .toLowerCase();
                    headerAlias.put(canonical, raw);
                }

                // ===== 4. 读取所有记录并按 Order ID 分组 =====
                List<CSVRecord> allRecords = csvParser.getRecords();
                if (allRecords == null || allRecords.isEmpty()) {
                    return 0;
                }

                Map<String, List<CSVRecord>> orderGroups = allRecords.stream()
                        .collect(Collectors.groupingBy(record ->
                                getCell(record, headerAlias, "Order ID").trim()
                        ));

                List<SalesOrderEntity> newOrders = new ArrayList<>();
                List<SalesOrderItemEntity> newItems = new ArrayList<>();
                Map<String, SalesOrderEntity> orderMap = new HashMap<>();

                // ===== 5. 遍历每一个订单（按平台订单号分组） =====
                for (Map.Entry<String, List<CSVRecord>> entry : orderGroups.entrySet()) {
                    String platformOrderNo = entry.getKey();
                    List<CSVRecord> records = entry.getValue();

                    if (records == null || records.isEmpty()) {
                        continue;
                    }

                    CSVRecord firstRecord = records.get(0);

                    // ---- 基本字段、时间、状态 ----
                    String orderStatusLabel = getCell(firstRecord, headerAlias, "Order Status");
                    String createdTimeStr   = getCell(firstRecord, headerAlias, "Created Time");
                    String paidTimeStr      = getCell(firstRecord, headerAlias, "Paid Time");

                    String orderStatusStr = statusDictMap.getOrDefault(
                            orderStatusLabel == null ? "" : orderStatusLabel.toUpperCase(),
                            "0"
                    );

                    SalesOrderEntity order = new SalesOrderEntity();
                    order.setPlatformCode(shop.getType().toString());
                    order.setShopId(shopId);
                    order.setPlatformOrderNo(platformOrderNo);
                    order.setOrderStatus(Integer.parseInt(orderStatusStr));
                    order.setOrderTime(parseTime(createdTimeStr));
                    order.setPayTime(parseTime(paidTimeStr));
                    order.setCurrencyCode("PHP"); // 先默认 PHP，后面再看要不要从文件取
                    order.setTrackingId(getCell(firstRecord, headerAlias, "Tracking ID"));
                    order.setShippingProvider(getCell(firstRecord, headerAlias, "Shipping Provider Name"));
                    // ---- 金额聚合（商品金额、折扣） ----
                    BigDecimal goodsAmount = BigDecimal.ZERO;
                    BigDecimal sellerDiscountTotal = BigDecimal.ZERO;
                    BigDecimal platformDiscountTotal = BigDecimal.ZERO;

                    for (CSVRecord record : records) {
                        BigDecimal skuSubtotalBefore = parseBigDecimalSafe(
                                getCell(record, headerAlias, "SKU Subtotal Before Discount"));
                        BigDecimal skuSellerDiscount = parseBigDecimalSafe(
                                getCell(record, headerAlias, "SKU Seller Discount"));
                        BigDecimal skuPlatformDiscount = parseBigDecimalSafe(
                                getCell(record, headerAlias, "SKU Platform Discount"));

                        goodsAmount = goodsAmount.add(skuSubtotalBefore);
                        sellerDiscountTotal = sellerDiscountTotal.add(skuSellerDiscount);
                        platformDiscountTotal = platformDiscountTotal.add(skuPlatformDiscount);
                    }

                    order.setGoodsAmount(goodsAmount);
                    // 商家折扣取反，因为是减收入
                    order.setSellerDiscountTotal(sellerDiscountTotal.negate());
                    order.setPlatformDiscountTotal(platformDiscountTotal);

                    order.setShippingFeeBuyer(parseBigDecimalSafe(
                            getCell(firstRecord, headerAlias, "Original Shipping Fee")));
                    order.setTaxAmount(parseBigDecimalSafe(
                            getCell(firstRecord, headerAlias, "Taxes")));
                    order.setBuyerPayAmount(parseBigDecimalSafe(
                            getCell(firstRecord, headerAlias, "Order Amount")));

                    newOrders.add(order);
                    orderMap.put(platformOrderNo, order);

                    // ===== 6. 明细行处理 =====
                    for (CSVRecord record : records) {
                        SalesOrderItemEntity item = new SalesOrderItemEntity();

                        // 冗余字段
                        item.setPlatformCode(order.getPlatformCode());
                        item.setPlatformOrderNo(platformOrderNo);
                        item.setShopId(shopId);
                        item.setCurrencyCode(order.getCurrencyCode());

                        // 商品 & SKU
                        String shopSkuCode = getCell(record, headerAlias, "Seller SKU");
                        item.setShopSkuCode(shopSkuCode);
                        item.setSkuId(skuMap.get(shopId + "_" + shopSkuCode));   // 可能为 null，后续你可以补一个兜底逻辑
                        item.setPlatformSkuCode(getCell(record, headerAlias, "SKU ID"));
                        item.setProductName(getCell(record, headerAlias, "Product Name"));
                        item.setVariation(getCell(record, headerAlias, "Variation"));

                        String qtyStr = getCell(record, headerAlias, "Quantity");
                        int qty = 0;
                        if (org.apache.commons.lang.StringUtils.isNotBlank(qtyStr)) {
                            qty = Integer.parseInt(qtyStr.trim());
                        }
                        item.setQuantity(qty);

                        BigDecimal listPrice = parseBigDecimalSafe(
                                getCell(record, headerAlias, "SKU Unit Original Price"));
                        item.setListPrice(listPrice);

                        BigDecimal lineAmount = parseBigDecimalSafe(
                                getCell(record, headerAlias, "SKU Subtotal Before Discount"));
                        item.setLineAmount(lineAmount);

                        // 成交单价 = 行金额 / 数量
                        if (qty > 0) {
                            item.setSalePrice(lineAmount.divide(
                                    new BigDecimal(qty), 4, BigDecimal.ROUND_HALF_UP));
                        } else {
                            item.setSalePrice(BigDecimal.ZERO);
                        }

                        // 折扣分摊
                        item.setSellerDiscountAlloc(parseBigDecimalSafe(
                                getCell(record, headerAlias, "SKU Seller Discount")));
                        item.setPlatformDiscountAlloc(parseBigDecimalSafe(
                                getCell(record, headerAlias, "SKU Platform Discount")));

                        // 运费税费分摊（暂时 0，以后可以根据业务加算法）
                        item.setShippingFeeAlloc(BigDecimal.ZERO);
                        item.setTaxAmountAlloc(BigDecimal.ZERO);

                        newItems.add(item);
                    }
                }

                // ===== 7. 批量保存主表 =====
                if (!newOrders.isEmpty()) {
                    List<String> orderNos = newOrders.stream().map(SalesOrderEntity::getPlatformOrderNo).collect(Collectors.toList());
                    salesOrderMapper.delete(Wrappers.<SalesOrderEntity>lambdaQuery().in(SalesOrderEntity::getPlatformOrderNo, orderNos));
                    this.saveBatch(newOrders, 500);
                }

                // ===== 8. 回填明细表的 orderId =====
                for (SalesOrderItemEntity item : newItems) {
                    SalesOrderEntity savedOrder = orderMap.get(item.getPlatformOrderNo());
                    if (savedOrder != null && savedOrder.getId() != null) {
                        item.setOrderId(savedOrder.getId());
                    } else {
                        throw new RuntimeException("无法获取订单 (" + item.getPlatformOrderNo() + ") 的主键 ID。");
                    }
                }

                // ===== 9. 批量保存明细表 =====
                if (!newItems.isEmpty()) {
                    List<String> orderNos = newItems.stream().map(SalesOrderItemEntity::getPlatformOrderNo).collect(Collectors.toList());
                    salesOrderItemMapper.delete(Wrappers.<SalesOrderItemEntity>lambdaQuery().in(SalesOrderItemEntity::getPlatformOrderNo, orderNos));
                    salesOrderItemService.saveOrUpdateBatch(newItems, 1000);
                }

                return newOrders.size();

            } catch (Exception e) {
                throw new RuntimeException("CSV 解析或数据转换错误: " + e.getMessage(), e);
            }
        }
    }

    /**
     * 根据逻辑列名，安全获取单元格内容
     */
    private String getCell(CSVRecord record,
                           Map<String, String> headerAlias,
                           String logicalName) {
        String canonical = logicalName.replace("\uFEFF", "").trim().toLowerCase();
        String realHeader = headerAlias.get(canonical);
        if (realHeader == null) {
            // 这里直接抛异常，方便排查到底是哪一列表头不匹配
            throw new RuntimeException("CSV 中未找到表头列: " + logicalName);
            // 如果你希望更“宽松”，可以改成：return "";
        }
        return record.get(realHeader);
    }

    /**
     * 金额解析：空字符串或 null 返回 0，避免 NFE
     */
    private BigDecimal parseBigDecimalSafe(String value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        String v = value.trim();
        if (v.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(v);
    }



    // 辅助方法 2: 时间字符串解析
    private Date parseTime(String timeStr) throws ParseException {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            return null;
        }

        timeStr = timeStr.trim().replace("\t", "").replace("\uFEFF", "");

        // TikTok 时间可能的格式合集
        String[] patterns = new String[]{
                "MM/dd/yyyy h:mm:ss a",   // 1 位小时，带秒，12 小时制
                "MM/dd/yyyy hh:mm:ss a",  // 2 位小时，带秒，12 小时制
                "MM/dd/yyyy h:mm a",      // 无秒
                "MM/dd/yyyy hh:mm a",     // 无秒，两位小时
                "MM/dd/yyyy HH:mm:ss",    // 24 小时制，带秒
                "MM/dd/yyyy HH:mm",       // 24 小时制，无秒
        };

        for (String p : patterns) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(p, Locale.US);
                sdf.setLenient(false);  // 严格模式，减少误解析
                return sdf.parse(timeStr);
            } catch (Exception ignored) {
            }
        }

        throw new ParseException("Unsupported date format: " + timeStr, 0);
    }


    // SalesOrderService.java
    public Map<String, Object> getDetailsByOrderNo(String platformOrderNo) {
        // 1. 查询订单主表数据
        SalesOrderEntity order = this.getOne(
                Wrappers.<SalesOrderEntity>lambdaQuery()
                        .eq(SalesOrderEntity::getPlatformOrderNo, platformOrderNo)
        );

        if (order == null) {
            return Collections.emptyMap();
        }

        // 2. 查询订单明细数据
        List<SalesOrderItemEntity> items = salesOrderItemService.list(Wrappers.<SalesOrderItemEntity>lambdaQuery().eq(SalesOrderItemEntity::getPlatformOrderNo, platformOrderNo));

        // 3. 聚合数据返回
        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("items", items);
        return result;
    }
}