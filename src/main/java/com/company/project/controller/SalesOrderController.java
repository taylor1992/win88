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
import com.company.project.entity.SalesOrderEntity;
import com.company.project.service.SalesOrderService;



/**
 * 销售订单主表（精简，利润计算用）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-11 08:48:50
 */
@Controller
@RequestMapping("/")
public class SalesOrderController {
    @Autowired
    private SalesOrderService salesOrderService;

    /**
    * 跳转到页面
    */
    @GetMapping("/index/salesOrder")
    public String salesOrder() {
        return "salesorder/list";
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("salesOrder/listByPage")
    @SaCheckPermission("salesOrder:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody SalesOrderEntity salesOrder){
        LambdaQueryWrapper<SalesOrderEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(SalesOrderEntity::getId);
        IPage<SalesOrderEntity> data = salesOrderService.page(salesOrder.getQueryPage(), queryWrapper);
        return DataResult.success(data);
    }


    @ApiOperation(value = "新增")
    @PostMapping("salesOrder/add")
    @SaCheckPermission("salesOrder:add")
    @ResponseBody
    public DataResult add(@RequestBody SalesOrderEntity salesOrder){
        String username = Win88Util.getUser().getUsername();
        //salesOrder.setCreatedBy(username);
            salesOrderService.save(salesOrder);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("salesOrder/delete")
    @SaCheckPermission("salesOrder:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids){
            salesOrderService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("salesOrder/update")
    @SaCheckPermission("salesOrder:update")
    @ResponseBody
    public DataResult update(@RequestBody SalesOrderEntity salesOrder){
        SysUser user = Win88Util.getUser();
        //salesOrder.setUpdatedBy(user.getUsername());
            salesOrderService.updateById(salesOrder);
        return DataResult.success();
    }



}
