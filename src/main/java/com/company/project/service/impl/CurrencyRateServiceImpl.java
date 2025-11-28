package com.company.project.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.common.utils.DateUtils;
import com.company.project.entity.CurrencyRateEntity;
import com.company.project.mapper.CurrencyRateMapper;
import com.company.project.service.CurrencyRateService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service("currencyRateService")
public class CurrencyRateServiceImpl extends ServiceImpl<CurrencyRateMapper, CurrencyRateEntity> implements CurrencyRateService {

    private static final String API_URL = "https://api.frankfurter.app/latest?from=CNY&to=PHP,USD";
    @Autowired
    RestTemplate restTemplate;

    @Override
    public void updateRates() {
        log.info("正在同步汇率...");
        Date date = new Date();
        String end = DateUtil.format(date,"yyyy-MM-dd");
        String start = DateUtil.format(DateUtil.offsetMonth(date,2),"yyyy-MM-dd");
        updateByDate(start,end,"CNY","PHP");
        log.info("汇率同步结束");
    }

    private void updateRate(String toCurrency,String fromCurrency,BigDecimal rate,String date){
        LambdaQueryWrapper<CurrencyRateEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CurrencyRateEntity::getToCurrency,toCurrency);
        queryWrapper.eq(CurrencyRateEntity::getFromCurrency,fromCurrency);
        queryWrapper.eq(CurrencyRateEntity::getRateDate,date);

        CurrencyRateEntity obj = getOne(queryWrapper);
        if(Objects.nonNull(obj)){
            obj.setRate(rate);
            obj.setCreatedAt(new Date());
            updateById(obj);
        }else{
            obj = new CurrencyRateEntity();
            obj.setToCurrency(toCurrency);
            obj.setFromCurrency(fromCurrency);
            obj.setRate(rate);
            obj.setRateDate(DateUtil.parse(date,"yyyy-MM-dd"));
            obj.setCreatedAt(new Date());
            save(obj);
        }

    }

    @Override
    public void updateByDate(String start,String end,String base,String symbols) {
        String url = String.format(
                "https://api.frankfurter.dev/v1/%s..%s?base=%s&symbols=%s",
                start, end,base,symbols
        );

        // 返回结构类似：
        // {
        //   "base": "CNY",
        //   "start_date": "2024-11-26",
        //   "end_date": "2025-11-26",
        //   "rates": {
        //      "2024-11-26": { "PHP": 8.12, "USD": 0.1382 },
        //      "2024-11-27": { "PHP": 8.13, "USD": 0.1385 },
        //      ...
        //   }
        // }

        ResponseEntity<JsonNode> resp = restTemplate.getForEntity(url, JsonNode.class);

        JsonNode body = resp.getBody();
        if (body == null || body.get("rates") == null) {
            return;
        }

        JsonNode ratesNode = body.get("rates");

        // 遍历每一天
        Iterator<String> dates = ratesNode.fieldNames();
        while (dates.hasNext()) {
            String dateStr = dates.next();          // 比如 "2025-11-22"

            JsonNode dayNode = ratesNode.get(dateStr);
            Iterator<Map.Entry<String, JsonNode>> it = dayNode.fields();

            // 每个币种一条记录，比如 PHP、USD
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                String toCurrency = entry.getKey();               // PHP 或 USD
                BigDecimal rate = entry.getValue().decimalValue();// 汇率

                // 写入数据库（upsert）
                updateRate("CNY", toCurrency, rate, dateStr);
            }
        }

        String dateFormat = "yyyy-MM-dd";
        List<String> dateList = DateUtils.getWeekendsBetween(DateUtil.parse(start,dateFormat), DateUtil.parse(end,dateFormat));
        for (String d : dateList) {
            DateTime dateTime = DateUtil.offsetDay(DateUtil.parse(d, "yyyy-MM-dd"), -2);
            String api = "https://api.frankfurter.dev/v1/"+DateUtil.format(dateTime,dateFormat)+"?base="+base+"&symbols="+symbols;
            ResponseEntity<Map> entity = restTemplate.getForEntity(api, Map.class);
            Map<String,Object> m = entity.getBody();
            String toCurrency = m.get("base").toString();
            Map<String,Object> rate = (Map<String, Object>) m.get("rates");
            String fromCurrency = "";
            BigDecimal rateValue = BigDecimal.ZERO;
            for (Map.Entry<String, Object> em : rate.entrySet()) {
                fromCurrency = em.getKey();
                rateValue = new BigDecimal(em.getValue().toString());
            }
            updateRate(toCurrency,fromCurrency,rateValue,d);
            log.info("获取{}的汇率 {} to {} ,rate is {}",d,toCurrency,fromCurrency,rateValue);
        }
    }

    @Override
    public void updateWeekend(String date) {
        String dateFormat = "yyyy-MM-dd";
        List<String> dateList = DateUtils.getWeekendsByMonthWithNow(DateUtil.parse(date, dateFormat));
        for (String d : dateList) {
            log.info("获取{}的汇率",d);
            DateTime dateTime = DateUtil.offsetDay(DateUtil.parse(d, "yyyy-MM-dd"), -2);
            String api = "https://api.frankfurter.dev/v1/"+DateUtil.format(dateTime,dateFormat)+"?base=CNY&symbols=PHP";
            ResponseEntity<Map> entity = restTemplate.getForEntity(api, Map.class);
            Map<String,Object> m = entity.getBody();
            String toCurrency = m.get("base").toString();
            Map<String,Object> rate = (Map<String, Object>) m.get("rates");
            String fromCurrency = "";
            BigDecimal rateValue = BigDecimal.ZERO;
            for (Map.Entry<String, Object> em : rate.entrySet()) {
                fromCurrency = em.getKey();
                rateValue = new BigDecimal(em.getValue().toString());
            }
            updateRate(toCurrency,fromCurrency,rateValue,d);
        }
    }


}