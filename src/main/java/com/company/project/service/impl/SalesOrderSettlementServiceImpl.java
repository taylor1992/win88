package com.company.project.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.SalesOrderSettlementMapper;
import com.company.project.entity.SalesOrderSettlementEntity;
import com.company.project.service.SalesOrderSettlementService;


@Service("salesOrderSettlementService")
public class SalesOrderSettlementServiceImpl extends ServiceImpl<SalesOrderSettlementMapper, SalesOrderSettlementEntity> implements SalesOrderSettlementService {


}