package com.company.project.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.ShopProductMapper;
import com.company.project.entity.ShopProductEntity;
import com.company.project.service.ShopProductService;


@Service("shopProductService")
public class ShopProductServiceImpl extends ServiceImpl<ShopProductMapper, ShopProductEntity> implements ShopProductService {


}