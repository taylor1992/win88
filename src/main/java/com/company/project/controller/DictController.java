package com.company.project.controller;

import com.company.project.common.enums.DictionaryEnums;
import com.company.project.common.utils.DataResult;
import com.company.project.entity.ProductEntity;
import com.company.project.entity.SkuEntity;
import com.company.project.entity.SupplierEntity;
import com.company.project.service.ProductService;
import com.company.project.service.SkuService;
import com.company.project.service.SupplierService;
import com.company.project.vo.resp.OptionVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
@AllArgsConstructor
@RestController
@RequestMapping("/api/dict")
public class DictController {


    private final SupplierService supplierService;

    private final SkuService skuService;

    private final ProductService productService;

    // 单个类型（兼容旧代码）
    @GetMapping("/{type}")
    public DataResult getOne(@PathVariable("type") String type) {
        List<OptionVO> list = resolveOptions(type);

        return DataResult.success(list);
    }

    // 批量类型：/api/dict/batch?types=shopType&types=productStatus&types=enable
    @GetMapping("/batch")
    public DataResult batch(@RequestParam("types") List<String> types) {
        Map<String, List<OptionVO>> data = new HashMap<>();
        for (String type : types) {
            data.put(type, resolveOptions(type));
        }
        return DataResult.success(data);
    }

    // 根据 type 生成对应的 Option 列表
    private List<OptionVO> resolveOptions(String type) {
        List<OptionVO> options = new ArrayList<>();
        if ("shopType".equals(type)) {
            return Arrays.stream(DictionaryEnums.ShopTypeEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("enable".equals(type)) {
            return Arrays.stream(DictionaryEnums.EnableEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("productStatus".equals(type)) {
            return Arrays.stream(DictionaryEnums.ProductStatusEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("active".equals(type)) {
            return Arrays.stream(DictionaryEnums.ActiveEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("purchaseStatus".equals(type)) {
            return Arrays.stream(DictionaryEnums.PurchaseStatusEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("currency".equals(type)) {
            return Arrays.stream(DictionaryEnums.CurrencyEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("allocationMethod".equals(type)) {
            return Arrays.stream(DictionaryEnums.AllocationMethodEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("logisticsMethod".equals(type)) {
            return Arrays.stream(DictionaryEnums.LogisticsMethodEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("warehouse".equals(type)) {
            return Arrays.stream(DictionaryEnums.WarehouseEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }

        if ("InventoryChangeType".equals(type)) {
            return Arrays.stream(DictionaryEnums.InventoryChangeType.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getDescEn()))
                    .collect(Collectors.toList());
        }
        if ("ExpenseStatus".equals(type)) {
            return Arrays.stream(DictionaryEnums.ExpenseStatus.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("ExpenseCategory".equals(type)) {
            return Arrays.stream(DictionaryEnums.ExpenseCategory.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("supplier".equals(type)) {
            List<SupplierEntity> list = supplierService.list();
            for (SupplierEntity se : list) {
                OptionVO vo = new OptionVO(se.getId().toString(), se.getName());
                options.add(vo);
            }
            return options;
        }
        if ("internalSku".equals(type)) {
            List<SkuEntity> list = skuService.list();
            //List<Long> pids = list.stream().map(SkuEntity::getProductId).collect(Collectors.toList());
            //Map<Long, ProductEntity> pMap = productService.listByIds(pids).stream().collect(Collectors.toMap(ProductEntity::getId, Function.identity()));
            for (SkuEntity se : list) {
                OptionVO vo = new OptionVO(se.getId().toString(), se.getSkuCode());
                options.add(vo);
            }
            return options;
        }
        if ("product".equals(type)) {
            List<ProductEntity> list = productService.list();
            for (ProductEntity se : list) {
                OptionVO vo = new OptionVO(se.getId().toString(), se.getProductName());
                options.add(vo);
            }
            return options;
        }
        // 未知类型返回空列表
        return Collections.emptyList();
    }
}


