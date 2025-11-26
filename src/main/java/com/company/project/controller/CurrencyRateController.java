package com.company.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.dev33.satoken.annotation.SaCheckPermission;
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

import com.company.project.entity.CurrencyRateEntity;
import com.company.project.service.CurrencyRateService;


/**
 * 汇率表（支持多币种、按日期生效）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-26 20:32:53
 */
@Controller
@RequestMapping("/")
public class CurrencyRateController {
    @Autowired
    private CurrencyRateService currencyRateService;

    /**
     * 跳转到页面
     */
    @GetMapping("/index/currencyRate")
    public String currencyRate() {
        return "currencyrate/list";
    }


    @ApiOperation(value = "查询分页数据")
    @PostMapping("currencyRate/listByPage")
    @SaCheckPermission("currencyRate:list")
    @ResponseBody
    public DataResult findListByPage(@RequestBody CurrencyRateEntity currencyRate) {
        LambdaQueryWrapper<CurrencyRateEntity> queryWrapper = Wrappers.lambdaQuery();
        if(StringUtils.isNotBlank(currencyRate.getStartDate())){
            queryWrapper.gt(CurrencyRateEntity::getRateDate,currencyRate.getStartDate());
        }
        if(StringUtils.isNotBlank(currencyRate.getEndDate())){
            queryWrapper.lt(CurrencyRateEntity::getRateDate,currencyRate.getEndDate());
        }
        if(StringUtils.isNotBlank(currencyRate.getToCurrency())){
            queryWrapper.eq(CurrencyRateEntity::getToCurrency,currencyRate.getToCurrency());
        }
        if(StringUtils.isNotBlank(currencyRate.getFromCurrency())){
            queryWrapper.eq(CurrencyRateEntity::getFromCurrency,currencyRate.getFromCurrency());
        }
        queryWrapper.orderByDesc(CurrencyRateEntity::getRateDate);
        IPage<CurrencyRateEntity> iPage = currencyRateService.page(currencyRate.getQueryPage(), queryWrapper);
        return DataResult.success(iPage);
    }

    @ApiOperation(value = "更新")
    @PutMapping("currencyRate/update")
    @SaCheckPermission("currencyRate:update")
    @ResponseBody
    public DataResult update(@RequestBody CurrencyRateEntity currencyRate) {
        currencyRateService.updateByDate(currencyRate.getStartDate(), currencyRate.getEndDate(), currencyRate.getToCurrency(), currencyRate.getFromCurrency());
        return DataResult.success();
    }


}
