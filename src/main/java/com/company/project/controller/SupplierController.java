package com.company.project.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.company.project.common.utils.Win88Util;
import com.company.project.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import java.util.List;

import com.company.project.common.utils.DataResult;

import com.company.project.entity.SupplierEntity;
import com.company.project.service.SupplierService;


/**
 * 供应商表
 *
 * @author wenbin
 * @email *****@mail.com
 * @date 2025-11-25 20:42:30
 */
@Controller
@RequestMapping("/")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    /**
     * 跳转到页面
     */
    @GetMapping("/index/supplier")
    public String supplier() {
        return "supplier/list";
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("supplier/listByPage")
    @SaCheckPermission("supplier:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody SupplierEntity supplier) {
        LambdaQueryWrapper<SupplierEntity> queryWrapper = Wrappers.lambdaQuery();
        //查询条件示例
        queryWrapper.like(supplier.getName() != null, SupplierEntity::getName, supplier.getName());
        queryWrapper.orderByDesc(SupplierEntity::getId);
        IPage<SupplierEntity> iPage = supplierService.page(supplier.getQueryPage(), queryWrapper);
        return DataResult.success(iPage);
    }


    @ApiOperation(value = "新增")
    @PostMapping("supplier/add")
    @SaCheckPermission("supplier:add")
    @ResponseBody
    public DataResult add(@RequestBody SupplierEntity supplier) {
        SysUser user = Win88Util.getUser();
        supplier.setCreateBy(user.getUsername());
        supplierService.save(supplier);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("supplier/delete")
    @SaCheckPermission("supplier:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        supplierService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("supplier/update")
    @SaCheckPermission("supplier:update")
    @ResponseBody
    public DataResult update(@RequestBody SupplierEntity supplier) {
        SysUser user = Win88Util.getUser();
        supplier.setUpdateBy(user.getUsername());
        supplier.setUpdateDate(new Date());
        supplierService.updateById(supplier);
        return DataResult.success();
    }


}
