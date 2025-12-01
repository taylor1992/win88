package com.company.project.controller;

import com.company.project.common.enums.DictionaryEnums;
import com.company.project.common.utils.DataResult;
import com.company.project.vo.resp.OptionVO;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/dict")
public class DictController {

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
        if ("shopType".equals(type)) {
            return Arrays.stream(DictionaryEnums.ShopTypeEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("enable".equals(type)) {
            return Arrays.stream(DictionaryEnums.EnableEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("productStatus".equals(type)) {
            return Arrays.stream(DictionaryEnums.ProductStatusEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("active".equals(type)) {
            return Arrays.stream(DictionaryEnums.ActiveEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("purchaseStatus".equals(type)) {
            return Arrays.stream(DictionaryEnums.PurchaseStatusEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        if ("currency".equals(type)) {
            return Arrays.stream(DictionaryEnums.CurrencyEnum.values())
                    .map(e -> new OptionVO(String.valueOf(e.getCode()), e.getLabel()))
                    .collect(Collectors.toList());
        }
        // 未知类型返回空列表
        return Collections.emptyList();
    }
}


