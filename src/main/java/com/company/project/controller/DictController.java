package com.company.project.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.company.project.common.enums.DictionaryEnums;
import com.company.project.common.utils.DataResult;
import com.company.project.entity.*;
import com.company.project.service.*;
import com.company.project.vo.resp.OptionVO;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/dict")
public class DictController {


    private final SupplierService supplierService;

    private final SkuService skuService;

    private final ProductService productService;

    private final SysDictService sysDictService;

    private final SysDictDetailService sysDictDetailService;

    private final ShopService shopService;

    // 单个类型（兼容旧代码）
    @GetMapping("/{type}")
    public DataResult getOne(@PathVariable("type") String type) {
        List<OptionVO> list = resolveOptions(type);

        return DataResult.success(list);
    }

    // 批量类型：/api/dict/batch?types=shopType&types=productStatus&types=enable
    @GetMapping("/batch")
    public DataResult batch(@RequestParam("types") List<String> types) {
        Map<String, List<OptionVO>> data = new HashMap<>();
        for (String type : types) {
            data.put(type, resolveOptions(type));
        }
        return DataResult.success(data);
    }

    // 根据 type 生成对应的 Option 列表
    private List<OptionVO> resolveOptions(String type) {

        SysDictEntity dict = sysDictService.getOne(Wrappers.<SysDictEntity>lambdaQuery().eq(SysDictEntity::getName, type));
        if (dict == null) {
            List<OptionVO>  options = new LinkedList<>();
            if ("supplier".equals(type)) {
                List<SupplierEntity> list = supplierService.list();
                for (SupplierEntity se : list) {
                    OptionVO vo = new OptionVO(se.getId().toString(), se.getName());
                    options.add(vo);
                }
                return options;
            }
            if ("internalSku".equals(type)) {
                List<SkuEntity> list = skuService.list();
                //List<Long> pids = list.stream().map(SkuEntity::getProductId).collect(Collectors.toList());
                //Map<Long, ProductEntity> pMap = productService.listByIds(pids).stream().collect(Collectors.toMap(ProductEntity::getId, Function.identity()));
                for (SkuEntity se : list) {
                    OptionVO vo = new OptionVO(se.getId().toString(), se.getSkuCode());
                    options.add(vo);
                }
                return options;
            }
            if ("product".equals(type)) {
                List<ProductEntity> list = productService.list();
                for (ProductEntity se : list) {
                    OptionVO vo = new OptionVO(se.getId().toString(), se.getProductName());
                    options.add(vo);
                }
                return options;
            }
            if ("Shop".equals(type)) {
                List<ShopEntity> list = shopService.list();
                for (ShopEntity se : list) {
                    OptionVO vo = new OptionVO(se.getShopId().toString(), se.getName());
                    options.add(vo);
                }
                return options;
            }
        }
        List<SysDictDetailEntity> list = sysDictDetailService.list(Wrappers.<SysDictDetailEntity>lambdaQuery().eq(SysDictDetailEntity::getDictId, dict.getId()));
        if (CollectionUtils.isEmpty(list)) return new ArrayList<>();
        return list.stream().map(e -> new OptionVO(e.getValue(), e.getLabel())).collect(Collectors.toList());

    }
}


