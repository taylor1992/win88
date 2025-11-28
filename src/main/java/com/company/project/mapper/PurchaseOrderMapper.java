package com.company.project.mapper;

import com.company.project.entity.PurchaseOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购单主表（多币种·费用分摊·跨国物流·海运空运单号）
 * 
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-28 22:20:48
 */
public interface PurchaseOrderMapper extends BaseMapper<PurchaseOrderEntity> {
	
}
