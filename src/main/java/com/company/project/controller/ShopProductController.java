package com.company.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.company.project.common.utils.DataResult;
import com.company.project.common.utils.Win88Util;
import com.company.project.entity.*;
import com.company.project.service.ProductService;
import com.company.project.service.ShopProductService;
import com.company.project.service.ShopService;
import com.company.project.service.ShopSkuService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 店铺产品表（平台商品 Listing，关联内部产品）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-28 13:11:18
 */
@Controller
@RequestMapping("/")
public class ShopProductController {
    @Autowired
    private ShopProductService shopProductService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopSkuService shopSkuService;

    /**
     * 跳转到页面
     */
    @GetMapping("/index/shopProduct")
    public String shopProduct(Model model) {
        List<ShopEntity> shopList = shopService.list();
        shopList.forEach(p -> {
            int type = p.getType();
            if (type == 1) {
                p.setName("Tiktok-" + p.getName());
            } else if (type == 2) {
                p.setName("Shopee-" + p.getName());
            } else if (type == 3) {
                p.setName("Lazada-" + p.getName());
            } else if (type == 4) {
                p.setName("Facebook-" + p.getName());
            } else if (type == 5) {
                p.setName("Other-" + p.getName());
            }
        });
        model.addAttribute("shop", shopList);
        List<ProductEntity> productEntityList = productService.list();
        model.addAttribute("product", productEntityList);
        return "shopproduct/list" ;
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("shopProduct/listByPage")
    @SaCheckPermission("shopProduct:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody ShopProductEntity shopProduct) {
        LambdaQueryWrapper<ShopProductEntity> queryWrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(shopProduct.getShopId())) {
            queryWrapper.eq(ShopProductEntity::getShopId, shopProduct.getShopId());
        }
        if (Objects.nonNull(shopProduct.getStatus())) {
            queryWrapper.eq(ShopProductEntity::getStatus, shopProduct.getStatus());
        }
        queryWrapper.orderByDesc(ShopProductEntity::getId);
        IPage<ShopProductEntity> iPage = shopProductService.page(shopProduct.getQueryPage(), queryWrapper);
        if (CollectionUtil.isNotEmpty(iPage.getRecords())) {
            List<String> shopIds = iPage.getRecords().stream().map(ShopProductEntity::getShopId).collect(Collectors.toList());
            List<ShopEntity> shopEntityList = shopService.listByIds(shopIds);
            List<ProductEntity> productEntityList = productService.listByIds(iPage.getRecords().stream().map(ShopProductEntity::getProductId).collect(Collectors.toList()));
            Map<String, ShopEntity> shopMap = shopEntityList.stream().collect(Collectors.toMap(ShopEntity::getShopId, Function.identity()));
            Map<Long, ProductEntity> productMap = productEntityList.stream().collect(Collectors.toMap(ProductEntity::getId, Function.identity()));
            for (ShopProductEntity record : iPage.getRecords()) {
                ShopEntity shop = shopMap.get(record.getShopId());
                record.setShopName(shop.getName());
                record.setPlatformCode(Win88Util.getPlatformCode(shop.getType()));
                record.setProductName(productMap.get(record.getProductId()).getProductName());
            }
        }
        return DataResult.success(iPage);
    }


    @ApiOperation(value = "新增")
    @PostMapping("shopProduct/add")
    @SaCheckPermission("shopProduct:add")
    @ResponseBody
    public DataResult add(@RequestBody ShopProductEntity shopProduct) {

        ShopEntity shop = shopService.getById(shopProduct.getShopId());
        shopProduct.setPlatformCode(Win88Util.getPlatformCode(shop.getType()));

        String username = Win88Util.getUser().getUsername();
        shopProduct.setCreatedBy(username);
        shopProductService.save(shopProduct);
        return DataResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("shopProduct/delete")
    @SaCheckPermission("shopProduct:delete")
    @ResponseBody
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        shopProductService.removeByIds(ids);
        //shopSkuService.remove(Wrappers.<ShopSkuEntity>query().in("shop_id", ids));
        return DataResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("shopProduct/update")
    @SaCheckPermission("shopProduct:update")
    @ResponseBody
    public DataResult update(@RequestBody ShopProductEntity shopProduct) {
        SysUser user = Win88Util.getUser();
        shopProduct.setUpdatedBy(user.getUsername());
        shopProductService.updateById(shopProduct);
        return DataResult.success();
    }


}
