package com.company.project.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.SalesOrderProfitSnapshotMapper;
import com.company.project.entity.SalesOrderProfitSnapshotEntity;
import com.company.project.service.SalesOrderProfitSnapshotService;


@Service("salesOrderProfitSnapshotService")
public class SalesOrderProfitSnapshotServiceImpl extends ServiceImpl<SalesOrderProfitSnapshotMapper, SalesOrderProfitSnapshotEntity> implements SalesOrderProfitSnapshotService {


}