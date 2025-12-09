package com.company.project.mapper;

import com.company.project.entity.InventoryLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存变更流水表（支持手工汇总调整，也预留店铺/订单维度）
 * 
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-04 22:18:16
 */
public interface InventoryLogMapper extends BaseMapper<InventoryLogEntity> {
	
}
