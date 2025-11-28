package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import com.company.project.common.utils.DataResult;
import com.company.project.common.utils.Win88Util;
import com.company.project.entity.SysUser;
import com.company.project.entity.PurchaseOrderEntity;
import com.company.project.service.PurchaseOrderService;



/**
 * 采购单主表（多币种·费用分摊·跨国物流·海运空运单号）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-28 22:20:48
 */
@Controller
@RequestMapping("/")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    /**
    * 跳转到页面
    */
    @GetMapping("/index/purchaseOrder")
    public String purchaseOrder() {
        return "purchaseorder/list";
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("purchaseOrder/listByPage")
    @SaCheckPermission("purchaseOrder:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody PurchaseOrderEntity purchaseOrder){
        LambdaQueryWrapper<PurchaseOrderEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(PurchaseOrderEntity::getId);
        IPage<PurchaseOrderEntity> data = purchaseOrderService.page(purchaseOrder.getQueryPage(), queryWrapper);
        return DataResult.success(data);
    }


    @ApiOperation(value = "新增")
    @PostMapping("purchaseOrder/add")
    @SaCheckPermission("purchaseOrder:add")
    @ResponseBody
    public DataResult add(@RequestBody PurchaseOrderEntity purchaseOrder){
        String username = Win88Util.getUser().getUsername();
        purchaseOrder.setCreatedBy(username);
            purchaseOrderService.save(purchaseOrder);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("purchaseOrder/delete")
    @SaCheckPermission("purchaseOrder:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids){
            purchaseOrderService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("purchaseOrder/update")
    @SaCheckPermission("purchaseOrder:update")
    @ResponseBody
    public DataResult update(@RequestBody PurchaseOrderEntity purchaseOrder){
        SysUser user = Win88Util.getUser();
        purchaseOrder.setUpdatedBy(user.getUsername());
            purchaseOrderService.updateById(purchaseOrder);
        return DataResult.success();
    }



}
