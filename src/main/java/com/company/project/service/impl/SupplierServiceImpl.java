package com.company.project.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.SupplierMapper;
import com.company.project.entity.SupplierEntity;
import com.company.project.service.SupplierService;


@Service("supplierService")
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, SupplierEntity> implements SupplierService {


}