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
import com.company.project.entity.SalesOrderSettlementEntity;
import com.company.project.service.SalesOrderSettlementService;



/**
 * 销售订单结算明细表（精简，利润计算用）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-11 08:51:21
 */
@Controller
@RequestMapping("/")
public class SalesOrderSettlementController {
    @Autowired
    private SalesOrderSettlementService salesOrderSettlementService;

    /**
    * 跳转到页面
    */
    @GetMapping("/index/salesOrderSettlement")
    public String salesOrderSettlement() {
        return "salesordersettlement/list";
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("salesOrderSettlement/listByPage")
    @SaCheckPermission("salesOrderSettlement:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody SalesOrderSettlementEntity salesOrderSettlement){
        LambdaQueryWrapper<SalesOrderSettlementEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(SalesOrderSettlementEntity::getId);
        IPage<SalesOrderSettlementEntity> data = salesOrderSettlementService.page(salesOrderSettlement.getQueryPage(), queryWrapper);
        return DataResult.success(data);
    }


    @ApiOperation(value = "新增")
    @PostMapping("salesOrderSettlement/add")
    @SaCheckPermission("salesOrderSettlement:add")
    @ResponseBody
    public DataResult add(@RequestBody SalesOrderSettlementEntity salesOrderSettlement){
        String username = Win88Util.getUser().getUsername();
        //salesOrderSettlement.setCreatedBy(username);
            salesOrderSettlementService.save(salesOrderSettlement);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("salesOrderSettlement/delete")
    @SaCheckPermission("salesOrderSettlement:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids){
            salesOrderSettlementService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("salesOrderSettlement/update")
    @SaCheckPermission("salesOrderSettlement:update")
    @ResponseBody
    public DataResult update(@RequestBody SalesOrderSettlementEntity salesOrderSettlement){
        SysUser user = Win88Util.getUser();
        //salesOrderSettlement.setUpdatedBy(user.getUsername());
            salesOrderSettlementService.updateById(salesOrderSettlement);
        return DataResult.success();
    }



}
