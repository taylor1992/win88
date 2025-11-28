package com.company.project.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.company.project.common.utils.Win88Util;
import com.company.project.entity.SysUser;
import org.apache.commons.lang.StringUtils;
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

import com.company.project.entity.ProductEntity;
import com.company.project.service.ProductService;


/**
 * 内部产品表（SPU，所有店铺共用）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-28 09:56:45
 */
@Controller
@RequestMapping("/")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 跳转到页面
     */
    @GetMapping("/index/product")
    public String product() {
        return "product/list";
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("product/listByPage")
    @SaCheckPermission("product:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody ProductEntity product) {
        LambdaQueryWrapper<ProductEntity> queryWrapper = Wrappers.lambdaQuery();
        if(StringUtils.isNotBlank(product.getProductCode())){
            queryWrapper.like(ProductEntity::getProductCode, product.getProductCode());
        }
        if(ObjectUtil.isNotNull(product.getStatus())){
            queryWrapper.eq(ProductEntity::getStatus, product.getStatus());
        }
        //查询条件示例
        queryWrapper.orderByDesc(ProductEntity::getId);
        IPage<ProductEntity> iPage = productService.page(product.getQueryPage(), queryWrapper);
        return DataResult.success(iPage);
    }


    @ApiOperation(value = "新增")
    @PostMapping("product/add")
    @SaCheckPermission("product:add")
    @ResponseBody
    public DataResult add(@RequestBody ProductEntity product) {
        String username = Win88Util.getUser().getUsername();
        product.setCreatedBy(username);
        productService.save(product);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("product/delete")
    @SaCheckPermission("product:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        productService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("product/update")
    @SaCheckPermission("product:update")
    @ResponseBody
    public DataResult update(@RequestBody ProductEntity product) {
        SysUser user = Win88Util.getUser();
        product.setUpdatedBy(user.getUsername());
        productService.updateById(product);
        return DataResult.success();
    }


}
