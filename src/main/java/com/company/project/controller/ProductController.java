package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.company.project.common.utils.Win88Util;
import com.company.project.entity.ShopEntity;
import com.company.project.entity.SysUser;
import com.company.project.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.company.project.common.utils.DataResult;

import com.company.project.entity.ProductEntity;
import com.company.project.service.ProductService;


/**
 * 产品
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-26 17:27:36
 */
@Controller
@RequestMapping("/")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ShopService shopService;

    /**
     * 跳转到页面
     */
    @GetMapping("/index/product")
    public String product(Model model) {
        List<ShopEntity> shopList = shopService.list();
        shopList.forEach(p->{
            int type = p.getType();
            if(type == 1){
                p.setName("Tiktok-"+p.getName());
            }else if(type == 2){
                p.setName("Shopee-"+p.getName());
            }else if(type == 3){
                p.setName("Lazada-"+p.getName());
            }else if(type == 4){
                p.setName("Facebook-"+p.getName());
            }else if(type == 5){
                p.setName("Other-"+p.getName());
            }
        });
        model.addAttribute("shop",shopList);
        return "product/list";
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("product/listByPage")
    @SaCheckPermission("product:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody ProductEntity product) {
        LambdaQueryWrapper<ProductEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(ProductEntity::getId);
        IPage<ProductEntity> iPage = productService.page(product.getQueryPage(), queryWrapper);

        if(iPage.getTotal()>0){
            List<ProductEntity> records = iPage.getRecords();
            List<String> shopIds = records.stream().map(ProductEntity::getShopId).collect(Collectors.toList());
            List<ShopEntity> shopList = shopService.listByIds(shopIds);
            Map<String, ShopEntity> shopMap = shopList.stream().collect(Collectors.toMap(ShopEntity::getShopId, Function.identity()));
            for (ProductEntity record : records) {
                ShopEntity shop = shopMap.get(record.getShopId());
                if(Objects.isNull(shop)){
                    continue;
                }
                record.setShopName(shop.getName());
                record.setShopType(shop.getType());
            }
        }

        return DataResult.success(iPage);
    }


    @ApiOperation(value = "新增")
    @PostMapping("product/add")
    @SaCheckPermission("product:add")
    @ResponseBody
    public DataResult add(@RequestBody ProductEntity product) {
        SysUser user = Win88Util.getUser();
        product.setCreateBy(user.getUsername());
        product.setCreateTime(new Date());
        product.setUpdateBy(user.getUsername());
        product.setUpdateTime(new Date());

        ShopEntity shop = shopService.getById(product.getShopId());
        product.setShopType(shop.getType());
        product.setShopName(shop.getName());

        ProductEntity pe = productService.getById(product.getId());
        if(Objects.nonNull(pe)){
            productService.updateById(product);
        }else {
            productService.save(product);
        }
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
        product.setUpdateBy(user.getUsername());
        product.setUpdateTime(new Date());
        productService.updateById(product);
        return DataResult.success();
    }


}
