package com.company.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.PurchaseOrderItemMapper;
import com.company.project.entity.PurchaseOrderItemEntity;
import com.company.project.service.PurchaseOrderItemService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("purchaseOrderItemService")
public class PurchaseOrderItemServiceImpl extends ServiceImpl<PurchaseOrderItemMapper, PurchaseOrderItemEntity> implements PurchaseOrderItemService {


    @Override
    public List<PurchaseOrderItemEntity> listByOrderId(Long orderId) {
        return this.list(new LambdaQueryWrapper<PurchaseOrderItemEntity>()
                .eq(PurchaseOrderItemEntity::getPurchaseOrderId, orderId)
                .orderByAsc(PurchaseOrderItemEntity::getId));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void replaceItems(Long orderId, List<PurchaseOrderItemEntity> items) {
// 删除旧明细
        this.remove(new LambdaQueryWrapper<PurchaseOrderItemEntity>()
                .eq(PurchaseOrderItemEntity::getPurchaseOrderId, orderId));

        // 插入新明细
        if (items != null && !items.isEmpty()) {
            this.saveBatch(items);
        }
    }
}