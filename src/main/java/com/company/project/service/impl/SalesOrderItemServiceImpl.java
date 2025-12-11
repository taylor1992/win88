package com.company.project.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.SalesOrderItemMapper;
import com.company.project.entity.SalesOrderItemEntity;
import com.company.project.service.SalesOrderItemService;


@Service("salesOrderItemService")
public class SalesOrderItemServiceImpl extends ServiceImpl<SalesOrderItemMapper, SalesOrderItemEntity> implements SalesOrderItemService {


}