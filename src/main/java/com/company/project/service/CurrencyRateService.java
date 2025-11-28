package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.CurrencyRateEntity;

/**
 * 汇率表（支持多币种、按日期生效）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-26 20:32:53
 */
public interface CurrencyRateService extends IService<CurrencyRateEntity> {
    void updateRates();

    void updateByDate(String start,String end,String toCurrency,String fromCurrency);

    void updateWeekend(String date);
}

