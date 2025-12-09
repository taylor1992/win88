package com.company.project.mapper;

import com.company.project.entity.PurchaseOrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购单明细表（多币种，含费用分摊）
 * 
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-02 22:08:01
 */
public interface PurchaseOrderItemMapper extends BaseMapper<PurchaseOrderItemEntity> {
	
}
