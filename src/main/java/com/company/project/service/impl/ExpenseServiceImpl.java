package com.company.project.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.ExpenseMapper;
import com.company.project.entity.ExpenseEntity;
import com.company.project.service.ExpenseService;


@Service("expenseService")
public class ExpenseServiceImpl extends ServiceImpl<ExpenseMapper, ExpenseEntity> implements ExpenseService {


}