package com.company.project.mapper;

import com.company.project.entity.InventoryDailySnapshotEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 每日库存快照表（EOD 日结库存，用于对账与审计）
 * 
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-09 22:02:07
 */
public interface InventoryDailySnapshotMapper extends BaseMapper<InventoryDailySnapshotEntity> {
	
}
