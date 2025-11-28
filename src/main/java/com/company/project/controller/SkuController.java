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

import com.company.project.entity.SkuEntity;
import com.company.project.service.SkuService;



/**
 * SKU 表：支持多平台、多店铺、变体规格
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-27 23:37:53
 */
@Controller
@RequestMapping("/")
public class SkuController {
    @Autowired
    private SkuService skuService;

    /**
    * 跳转到页面
    */
    @GetMapping("/index/sku")
    public String sku() {
        return "sku/list";
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("sku/listByPage")
    @SaCheckPermission("sku:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody SkuEntity sku){
        LambdaQueryWrapper<SkuEntity> queryWrapper = Wrappers.lambdaQuery();
        //查询条件示例
        queryWrapper.orderByDesc(SkuEntity::getId);
        IPage<SkuEntity> iPage = skuService.page(sku.getQueryPage(), queryWrapper);
        return DataResult.success(iPage);
    }


    @ApiOperation(value = "新增")
    @PostMapping("sku/add")
    @SaCheckPermission("sku:add")
    @ResponseBody
    public DataResult add(@RequestBody SkuEntity sku){
            skuService.save(sku);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("sku/delete")
    @SaCheckPermission("sku:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids){
            skuService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("sku/update")
    @SaCheckPermission("sku:update")
    @ResponseBody
    public DataResult update(@RequestBody SkuEntity sku){
            skuService.updateById(sku);
        return DataResult.success();
    }



}
