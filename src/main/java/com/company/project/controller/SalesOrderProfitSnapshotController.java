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
import com.company.project.entity.SalesOrderProfitSnapshotEntity;
import com.company.project.service.SalesOrderProfitSnapshotService;



/**
 * 销售订单利润快照表（收入/成本/费用/毛利/净利）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-11 08:52:17
 */
@Controller
@RequestMapping("/")
public class SalesOrderProfitSnapshotController {
    @Autowired
    private SalesOrderProfitSnapshotService salesOrderProfitSnapshotService;

    /**
    * 跳转到页面
    */
    @GetMapping("/index/salesOrderProfitSnapshot")
    public String salesOrderProfitSnapshot() {
        return "salesorderprofitsnapshot/list";
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("salesOrderProfitSnapshot/listByPage")
    @SaCheckPermission("salesOrderProfitSnapshot:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody SalesOrderProfitSnapshotEntity salesOrderProfitSnapshot){
        LambdaQueryWrapper<SalesOrderProfitSnapshotEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(SalesOrderProfitSnapshotEntity::getId);
        IPage<SalesOrderProfitSnapshotEntity> data = salesOrderProfitSnapshotService.page(salesOrderProfitSnapshot.getQueryPage(), queryWrapper);
        return DataResult.success(data);
    }


    @ApiOperation(value = "新增")
    @PostMapping("salesOrderProfitSnapshot/add")
    @SaCheckPermission("salesOrderProfitSnapshot:add")
    @ResponseBody
    public DataResult add(@RequestBody SalesOrderProfitSnapshotEntity salesOrderProfitSnapshot){
        String username = Win88Util.getUser().getUsername();
        //salesOrderProfitSnapshot.setCreatedBy(username);
            salesOrderProfitSnapshotService.save(salesOrderProfitSnapshot);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("salesOrderProfitSnapshot/delete")
    @SaCheckPermission("salesOrderProfitSnapshot:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids){
            salesOrderProfitSnapshotService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("salesOrderProfitSnapshot/update")
    @SaCheckPermission("salesOrderProfitSnapshot:update")
    @ResponseBody
    public DataResult update(@RequestBody SalesOrderProfitSnapshotEntity salesOrderProfitSnapshot){
        SysUser user = Win88Util.getUser();
        //salesOrderProfitSnapshot.setUpdatedBy(user.getUsername());
            salesOrderProfitSnapshotService.updateById(salesOrderProfitSnapshot);
        return DataResult.success();
    }



}
