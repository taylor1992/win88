package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.company.project.common.enums.DictionaryEnums;
import com.company.project.common.exception.BusinessException;
import com.company.project.entity.InventoryLogEntity;
import com.company.project.mapper.InventoryLogMapper;
import com.company.project.vo.req.InventoryAdjustDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.InventoryMapper;
import com.company.project.entity.InventoryEntity;
import com.company.project.service.InventoryService;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service("inventoryService")
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, InventoryEntity> implements InventoryService {

    private final InventoryLogMapper inventoryLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void adjust(InventoryAdjustDTO dto) {
        LambdaQueryWrapper<InventoryEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(InventoryEntity::getWarehouseId, dto.getWarehouseId());
        queryWrapper.eq(InventoryEntity::getSkuId, dto.getSkuId());
        InventoryEntity inv = getOne(queryWrapper);
        if (inv == null) {
            inv = new InventoryEntity();
            inv.setWarehouseId(dto.getWarehouseId());
            inv.setSkuId(dto.getSkuId());
            inv.setQtyOnHand(0);
            inv.setQtyReserved(0);
            inv.setQtyAvailable(0);
            save(inv);
        }

        int before = inv.getQtyOnHand();
        int after = before + dto.getChangeQty();
        if (after < 0) {
            throw new BusinessException("Stock after adjustment cannot be negative.");
        }
        inv.setUpdatedBy(dto.getUpdatedBy());
        inv.setQtyOnHand(after);
        inv.setQtyAvailable(after - inv.getQtyReserved());
        updateById(inv);

        InventoryLogEntity log = new InventoryLogEntity();
        log.setWarehouseId(dto.getWarehouseId());
        log.setSkuId(dto.getSkuId());
        log.setChangeQty(dto.getChangeQty());
        log.setChangeType(dto.getChangeType());
        log.setQtyBefore(before);
        log.setQtyAfter(after);
        log.setRemark(dto.getRemark());
        log.setUpdatedBy(dto.getUpdatedBy());
        log.setCreatedBy(dto.getUpdatedBy());
        inventoryLogMapper.insert(log);
    }
}