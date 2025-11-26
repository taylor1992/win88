package com.company.project.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.company.project.mapper.ShopMapper;
import com.company.project.entity.ShopEntity;
import com.company.project.service.ShopService;


@Service("shopService")
public class ShopServiceImpl extends ServiceImpl<ShopMapper, ShopEntity> implements ShopService {


}