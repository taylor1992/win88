package com.company.project.mapper;

import com.company.project.entity.ShopSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 店铺 SKU 映射表：平台 SKU → 内部 SKU 的绑定关系
 * 
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-28 21:44:45
 */
public interface ShopSkuMapper extends BaseMapper<ShopSkuEntity> {
	
}
