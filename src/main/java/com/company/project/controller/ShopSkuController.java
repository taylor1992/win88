package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.company.project.entity.ShopEntity;
import com.company.project.entity.SkuEntity;
import com.company.project.service.ShopService;
import com.company.project.service.SkuService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.company.project.common.utils.DataResult;
import com.company.project.common.utils.Win88Util;
import com.company.project.entity.SysUser;
import com.company.project.entity.ShopSkuEntity;
import com.company.project.service.ShopSkuService;

import javax.annotation.Resource;


/**
 * 店铺 SKU 映射表：平台 SKU → 内部 SKU 的绑定关系
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-28 21:44:45
 */
@Controller
@RequestMapping("/")
public class ShopSkuController {
    @Autowired
    private ShopSkuService shopSkuService;

    @Autowired
    private ShopService shopService;

    @Resource
    private SkuService skuService;

    /**
     * 跳转到页面
     */
    @GetMapping("/index/shopSku")
    public String shopSku(Model model) {
        List<ShopEntity> list = shopService.list();
        List<SkuEntity> skuList = skuService.list();
        list.forEach(p->{
            p.setName(Win88Util.getPlatformCode(p.getType())+"-"+p.getName());
        });

        model.addAttribute("sku", skuList);
        model.addAttribute("shop", list);

        return "shopsku/list" ;
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("shopSku/listByPage")
    @SaCheckPermission("shopSku:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody ShopSkuEntity shopSku) {
        LambdaQueryWrapper<ShopSkuEntity> queryWrapper = Wrappers.lambdaQuery();
        if(StringUtils.isNotBlank(shopSku.getShopId())) {
            queryWrapper.eq(ShopSkuEntity::getShopId, shopSku.getShopId());
        }
        queryWrapper.orderByDesc(ShopSkuEntity::getId);
        IPage<ShopSkuEntity> iPage = shopSkuService.page(shopSku.getQueryPage(), queryWrapper);
        List<ShopSkuEntity> records = iPage.getRecords();
        if(CollectionUtils.isNotEmpty(records)) {
            List<String> shopIds = records.stream().map(ShopSkuEntity::getShopId).collect(Collectors.toList());
            Map<String, ShopEntity> shopMap = shopService.listByIds(shopIds).stream().collect(Collectors.toMap(ShopEntity::getShopId, Function.identity()));
            records.stream().forEach(shop -> {
                shop.setPlatformCode(Win88Util.getPlatformCode(shopMap.get(shop.getShopId()).getType()));
            });
        }
        return DataResult.success(iPage);
    }


    @ApiOperation(value = "新增")
    @PostMapping("shopSku/add")
    @SaCheckPermission("shopSku:add")
    @ResponseBody
    public DataResult add(@RequestBody ShopSkuEntity shopSku) {
        shopSkuService.save(shopSku);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("shopSku/delete")
    @SaCheckPermission("shopSku:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        shopSkuService.removeByIds(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("shopSku/update")
    @SaCheckPermission("shopSku:update")
    @ResponseBody
    public DataResult update(@RequestBody ShopSkuEntity shopSku) {
        shopSkuService.updateById(shopSku);
        return DataResult.success();
    }


}
