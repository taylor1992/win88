package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.PurchaseOrderItemEntity;

import java.util.List;

/**
 * 采购单明细表（多币种，含费用分摊）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-02 22:08:01
 */
public interface PurchaseOrderItemService extends IService<PurchaseOrderItemEntity> {

    /**
     * 按采购单 ID 查询全部明细
     */
    List<PurchaseOrderItemEntity> listByOrderId(Long orderId);

    /**
     * 替换此采购单下的所有明细（先删后插）
     */
    void replaceItems(Long orderId, List<PurchaseOrderItemEntity> items);
}

