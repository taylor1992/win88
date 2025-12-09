package com.company.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.company.project.common.enums.DictionaryEnums;
import com.company.project.common.utils.DataResult;
import com.company.project.common.utils.Win88Util;
import com.company.project.entity.PurchaseOrderEntity;
import com.company.project.entity.SysUser;
import com.company.project.service.InventoryService;
import com.company.project.service.PurchaseOrderItemService;
import com.company.project.service.PurchaseOrderService;
import com.company.project.vo.req.InventoryAdjustDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * 采购单主表（多币种·费用分摊·跨国物流·海运空运单号）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-28 22:20:48
 */
@Controller
@RequestMapping("/")
@AllArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    private final InventoryService inventoryService;

    private final PurchaseOrderItemService purchaseOrderItemService;

    /**
     * 跳转到页面
     */
    @GetMapping("/index/purchaseOrder")
    public String purchaseOrder() {
        return "purchaseorder/list" ;
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("purchaseOrder/listByPage")
    @SaCheckPermission("purchaseOrder:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody PurchaseOrderEntity purchaseOrder) {
        LambdaQueryWrapper<PurchaseOrderEntity> queryWrapper = Wrappers.lambdaQuery();
        if(StringUtils.isNotBlank(purchaseOrder.getPurchaseOrderNo())){
            queryWrapper.eq(PurchaseOrderEntity::getPurchaseOrderNo,purchaseOrder.getPurchaseOrderNo());
        }
        if(StringUtils.isNotBlank(purchaseOrder.getLogisticsTrackingNo())){
            queryWrapper.eq(PurchaseOrderEntity::getLogisticsTrackingNo,purchaseOrder.getLogisticsTrackingNo());
        }
        if(Objects.nonNull(purchaseOrder.getWarehouseInTimeEnd())){
            queryWrapper.le(PurchaseOrderEntity::getWarehouseInTime,purchaseOrder.getWarehouseInTimeEnd());
        }
        if(Objects.nonNull(purchaseOrder.getWarehouseInTimeBegin())){
            queryWrapper.ge(PurchaseOrderEntity::getWarehouseInTime,purchaseOrder.getWarehouseInTimeBegin());
        }
        queryWrapper.orderByDesc(PurchaseOrderEntity::getId);
        IPage<PurchaseOrderEntity> data = purchaseOrderService.page(purchaseOrder.getQueryPage(), queryWrapper);

        return DataResult.success(data);
    }


    @ApiOperation(value = "新增")
    @PostMapping("purchaseOrder/add")
    @SaCheckPermission("purchaseOrder:add")
    @ResponseBody
    public DataResult add(@RequestBody PurchaseOrderEntity purchaseOrder) {
        String username = Win88Util.getUser().getUsername();
        purchaseOrder.setCreatedBy(username);
        purchaseOrder.setUpdatedBy(username);
        purchaseOrder.setFxRateDate(new Date());
        purchaseOrderService.save(purchaseOrder);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("purchaseOrder/delete")
    @SaCheckPermission("purchaseOrder:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        purchaseOrderService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("purchaseOrder/update")
    @SaCheckPermission("purchaseOrder:update")
    @ResponseBody
    public DataResult update(@RequestBody PurchaseOrderEntity purchaseOrder) {
        SysUser user = Win88Util.getUser();
        purchaseOrder.setUpdatedBy(user.getUsername());
        purchaseOrderService.updateById(purchaseOrder);

        purchaseOrderItemService.listByOrderId(purchaseOrder.getId()).forEach(item -> {
            InventoryAdjustDTO dto = new InventoryAdjustDTO();
            dto.setChangeQty(item.getQuantity());
            dto.setSkuId(item.getSkuId());
            dto.setWarehouseId((long)DictionaryEnums.WarehouseEnum.LAS_PINAS.getCode());
            dto.setChangeType(DictionaryEnums.InventoryChangeType.PURCHASE_IN.getCode());
            dto.setRemark("入库更新");
            inventoryService.adjust(dto);
        });

        return DataResult.success();
    }


}
