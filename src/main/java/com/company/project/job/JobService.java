package com.company.project.job;

import com.company.project.service.CurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class JobService {

    @Autowired
    CurrencyRateService rateService;
    @Scheduled(cron = "0 0 2 * * ?") // 每天凌晨2点更新
    public void syncCurrencyRate(){
        rateService.updateRates();
    }
}
