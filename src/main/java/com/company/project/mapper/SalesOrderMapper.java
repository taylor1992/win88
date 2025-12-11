package com.company.project.mapper;

import com.company.project.entity.SalesOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 销售订单主表（精简，利润计算用）
 * 
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-11 08:48:50
 */
public interface SalesOrderMapper extends BaseMapper<SalesOrderEntity> {
	
}
