package com.company.project.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.InventoryLogMapper;
import com.company.project.entity.InventoryLogEntity;
import com.company.project.service.InventoryLogService;


@Service("inventoryLogService")
public class InventoryLogServiceImpl extends ServiceImpl<InventoryLogMapper, InventoryLogEntity> implements InventoryLogService {


}