package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.InventoryEntity;
import com.company.project.vo.req.InventoryAdjustDTO;

/**
 * 当前库存表（仓库 + 内部 SKU）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-03 23:23:49
 */
public interface InventoryService extends IService<InventoryEntity> {

    void adjust(InventoryAdjustDTO dto);
}

