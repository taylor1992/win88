package com.company.project.mapper;

import com.company.project.entity.InventoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 当前库存表（仓库 + 内部 SKU）
 * 
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-03 23:23:49
 */
public interface InventoryMapper extends BaseMapper<InventoryEntity> {
	
}
