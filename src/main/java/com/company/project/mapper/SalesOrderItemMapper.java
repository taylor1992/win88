package com.company.project.mapper;

import com.company.project.entity.SalesOrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 销售订单明细表（精简，SKU 级金额）
 * 
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-11 20:05:27
 */
public interface SalesOrderItemMapper extends BaseMapper<SalesOrderItemEntity> {
	
}
