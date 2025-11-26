package com.company.project.mapper;

import com.company.project.entity.CurrencyRateEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 汇率表（支持多币种、按日期生效）
 * 
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-26 20:32:53
 */
public interface CurrencyRateMapper extends BaseMapper<CurrencyRateEntity> {
	
}
