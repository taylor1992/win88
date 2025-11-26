package com.company.project.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.ProductMapper;
import com.company.project.entity.ProductEntity;
import com.company.project.service.ProductService;


@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductService {


}