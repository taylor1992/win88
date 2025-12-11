package com.company.project.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.SalesOrderMapper;
import com.company.project.entity.SalesOrderEntity;
import com.company.project.service.SalesOrderService;


@Service("salesOrderService")
public class SalesOrderServiceImpl extends ServiceImpl<SalesOrderMapper, SalesOrderEntity> implements SalesOrderService {


}