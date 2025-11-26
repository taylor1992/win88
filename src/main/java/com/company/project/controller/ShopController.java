package com.company.project.controller;

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
import java.util.Objects;

import com.company.project.common.utils.DataResult;

import com.company.project.entity.ShopEntity;
import com.company.project.service.ShopService;


/**
 * @author wenbin
 * @email *****@mail.com
 * @date 2025-11-25 21:49:35
 */
@Controller
@RequestMapping("/")
public class ShopController {
    @Autowired
    private ShopService shopService;

    /**
     * 跳转到页面
     */
    @GetMapping("/index/shop")
    public String shop() {
        return "shop/list";
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("shop/listByPage")
    @SaCheckPermission("shop:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody ShopEntity shop) {
        LambdaQueryWrapper<ShopEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(ShopEntity::getCreateTime);
        IPage<ShopEntity> iPage = shopService.page(shop.getQueryPage(), queryWrapper);
        return DataResult.success(iPage);
    }


    @ApiOperation(value = "新增")
    @PostMapping("shop/add")
    @SaCheckPermission("shop:add")
    @ResponseBody
    public DataResult add(@RequestBody ShopEntity shop) {
        SysUser user = Win88Util.getUser();
        shop.setCreateBy(user.getUsername());
        shop.setCreateTime(new Date());
        shop.setUpdateBy(user.getUsername());
        shop.setUpdateTime(new Date());

        ShopEntity ist = shopService.getById(shop.getShopId());
        if(Objects.nonNull(ist)){
            shopService.updateById(shop);
        }else {
            shopService.save(shop);
        }
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("shop/delete")
    @SaCheckPermission("shop:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        shopService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("shop/update")
    @SaCheckPermission("shop:update")
    @ResponseBody
    public DataResult update(@RequestBody ShopEntity shop) {
        SysUser user = Win88Util.getUser();
        shop.setUpdateBy(user.getUsername());
        shop.setUpdateTime(new Date());
        shopService.updateById(shop);
        return DataResult.success();
    }


}
