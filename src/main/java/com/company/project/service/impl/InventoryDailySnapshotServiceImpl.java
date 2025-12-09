package com.company.project.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.InventoryDailySnapshotMapper;
import com.company.project.entity.InventoryDailySnapshotEntity;
import com.company.project.service.InventoryDailySnapshotService;


@Service("inventoryDailySnapshotService")
public class InventoryDailySnapshotServiceImpl extends ServiceImpl<InventoryDailySnapshotMapper, InventoryDailySnapshotEntity> implements InventoryDailySnapshotService {


}