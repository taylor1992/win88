package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.entity.PurchaseOrderEntity;
import com.company.project.service.PurchaseOrderService;
import com.company.project.vo.req.PurchaseItemSaveReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import com.company.project.common.utils.DataResult;
import com.company.project.common.utils.Win88Util;
import com.company.project.entity.SysUser;
import com.company.project.entity.PurchaseOrderItemEntity;
import com.company.project.service.PurchaseOrderItemService;


/**
 * 采购单明细表（多币种，含费用分摊）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-02 22:08:01
 */
@Controller
@RequestMapping("/")
public class PurchaseOrderItemController {
    @Autowired
    private PurchaseOrderItemService purchaseOrderItemService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    /**
     * 跳转到页面
     */
    @GetMapping("/index/purchaseOrderItem")
    public String purchaseOrderItem(@RequestParam("purchaseOrderId") Long purchaseOrderId, Model model) {
        model.addAttribute("purchaseOrderId", purchaseOrderId);
        return "purchaseorderitem/list" ;
    }

    /**
     * 查询指定采购单的全部明细
     */
    @GetMapping("purchaseOrderItem/list")
    @ResponseBody
    public DataResult list(@RequestParam("orderId") Long orderId) {
        List<PurchaseOrderItemEntity> list = purchaseOrderItemService.listByOrderId(orderId);
        return DataResult.success(list);
    }

    /**
     * 批量保存明细（全量覆盖）
     */
    @PostMapping("purchaseOrderItem/saveBatch")
    @ResponseBody
    public DataResult saveBatch(@RequestBody PurchaseItemSaveReq req) {

        Long orderId = req.getPurchaseOrderId();
        if (orderId == null) {
            return DataResult.getResult(BaseResponseCode.OPERATION_ERRO.getCode(), "采购单ID不能为空");
        }

        AtomicReference<BigDecimal> orderTotalAmount = new AtomicReference<>(BigDecimal.ZERO);
        List<PurchaseOrderItemEntity> entityList = new ArrayList<>();

        if (req.getItems() != null) {
            req.getItems().forEach(dto -> {
                // 最基本的必填校验
                if (dto.getSkuId() == null || dto.getQuantity() == null || dto.getUnitPrice() == null) {
                    return; // 跳过不完整的行
                }

                PurchaseOrderItemEntity item = new PurchaseOrderItemEntity();
                item.setPurchaseOrderId(orderId);
                item.setSkuId(dto.getSkuId());
                item.setQuantity(dto.getQuantity());
                item.setUnitPrice(dto.getUnitPrice());
                item.setRemark(dto.getRemark());
                item.setAmountFreightAlloc(dto.getAmountFreightAlloc());
                item.setAmountOtherAlloc(dto.getAmountOtherAlloc());
                item.setWeightKgPerUnit(dto.getWeightKgPerUnit());

                // 商品小计 = 单价 * 数量
                BigDecimal qty = new BigDecimal(dto.getQuantity());
                BigDecimal goodsSubtotal = dto.getUnitPrice().multiply(qty);
                item.setAmountGoodsSubtotal(goodsSubtotal);
                orderTotalAmount.set(orderTotalAmount.get().add(goodsSubtotal));

                // 总重量 = 单件重量 * 数量
                if (dto.getWeightKgPerUnit() != null) {
                    item.setWeightKgTotal(dto.getWeightKgPerUnit().multiply(qty));
                }

                // 本行总成本 = 商品小计 + 分摊运费 + 分摊其他费用
                BigDecimal costTotal = goodsSubtotal
                        .add(nvl(dto.getAmountFreightAlloc()))
                        .add(nvl(dto.getAmountOtherAlloc()));
                item.setAmountCostTotal(costTotal);

                entityList.add(item);
            });
        }

        purchaseOrderItemService.replaceItems(orderId, entityList);
        PurchaseOrderEntity poe = purchaseOrderService.getById(orderId);
        poe.setAmountGoodsTotal(orderTotalAmount.get());
        purchaseOrderService.updateById(poe);
        return DataResult.success();
    }

    private BigDecimal nvl(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }
    /**
     * 打开明细编辑页
     */
    @GetMapping("purchaseOrderItem/detail")
    public String detail(@RequestParam("orderId") Long orderId, @RequestParam("status") Long status, Model model) {
        //PurchaseOrderEntity poe = purchaseOrderService.getById(orderId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("status", status);
        //model.addAttribute("poe", poe);
        return "purchaseorder/item";   // 对应 templates/purchaseorder/item.html
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("purchaseOrderItem/listByPage")
    @SaCheckPermission("purchaseOrderItem:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody PurchaseOrderItemEntity purchaseOrderItem) {
        LambdaQueryWrapper<PurchaseOrderItemEntity> queryWrapper = Wrappers.lambdaQuery();
        if(Objects.nonNull(purchaseOrderItem.getPurchaseOrderId())){
            queryWrapper.eq(PurchaseOrderItemEntity::getPurchaseOrderId,purchaseOrderItem.getPurchaseOrderId());
        }
        queryWrapper.orderByDesc(PurchaseOrderItemEntity::getId);
        IPage<PurchaseOrderItemEntity> data = purchaseOrderItemService.page(purchaseOrderItem.getQueryPage(), queryWrapper);
        return DataResult.success(data);
    }


    @ApiOperation(value = "新增")
    @PostMapping("purchaseOrderItem/add")
    @SaCheckPermission("purchaseOrderItem:add")
    @ResponseBody
    public DataResult add(@RequestBody PurchaseOrderItemEntity purchaseOrderItem) {
        String username = Win88Util.getUser().getUsername();
        purchaseOrderItemService.save(purchaseOrderItem);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("purchaseOrderItem/delete")
    @SaCheckPermission("purchaseOrderItem:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        purchaseOrderItemService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("purchaseOrderItem/update")
    @SaCheckPermission("purchaseOrderItem:update")
    @ResponseBody
    public DataResult update(@RequestBody PurchaseOrderItemEntity purchaseOrderItem) {
        SysUser user = Win88Util.getUser();
        purchaseOrderItemService.updateById(purchaseOrderItem);
        return DataResult.success();
    }


}
