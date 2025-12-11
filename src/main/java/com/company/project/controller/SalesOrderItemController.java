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
import com.company.project.entity.SalesOrderItemEntity;
import com.company.project.service.SalesOrderItemService;


/**
 * 销售订单明细表（精简，SKU 级金额）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-11 20:05:27
 */
@Controller
@RequestMapping("/")
public class SalesOrderItemController {
    @Autowired
    private SalesOrderItemService salesOrderItemService;

    /**
     * 跳转到页面
     */
    @GetMapping("/index/salesOrderItem")
    public String salesOrderItem() {
        return "salesorderitem/list" ;
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("salesOrderItem/listByPage")
    @SaCheckPermission("salesOrderItem:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody SalesOrderItemEntity salesOrderItem) {
        LambdaQueryWrapper<SalesOrderItemEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(SalesOrderItemEntity::getId);
        IPage<SalesOrderItemEntity> data = salesOrderItemService.page(salesOrderItem.getQueryPage(), queryWrapper);
        return DataResult.success(data);
    }


    @ApiOperation(value = "新增")
    @PostMapping("salesOrderItem/add")
    @SaCheckPermission("salesOrderItem:add")
    @ResponseBody
    public DataResult add(@RequestBody SalesOrderItemEntity salesOrderItem) {
        String username = Win88Util.getUser().getUsername();
        //salesOrderItem.setCreatedBy(username);
        salesOrderItemService.save(salesOrderItem);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("salesOrderItem/delete")
    @SaCheckPermission("salesOrderItem:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        salesOrderItemService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("salesOrderItem/update")
    @SaCheckPermission("salesOrderItem:update")
    @ResponseBody
    public DataResult update(@RequestBody SalesOrderItemEntity salesOrderItem) {
        SysUser user = Win88Util.getUser();
        //salesOrderItem.setUpdatedBy(user.getUsername());
        salesOrderItemService.updateById(salesOrderItem);
        return DataResult.success();
    }


}
