package com.company.project.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.PurchaseOrderMapper;
import com.company.project.entity.PurchaseOrderEntity;
import com.company.project.service.PurchaseOrderService;


@Service("purchaseOrderService")
public class PurchaseOrderServiceImpl extends ServiceImpl<PurchaseOrderMapper, PurchaseOrderEntity> implements PurchaseOrderService {


}