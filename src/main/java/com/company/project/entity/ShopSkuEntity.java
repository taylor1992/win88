package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.company.project.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldFill;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 店铺 SKU 映射表：平台 SKU → 内部 SKU 的绑定关系
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-28 21:44:45
 */
@Data
@TableName("shop_sku")
public class ShopSkuEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 店铺 SKU 主键 ID
	 */
	@TableId("id")
	private Long id;

	/**
	 * 店铺 ID（TikTok 多店铺适配）
	 */
		@TableField("shop_id")
		private String shopId;

	/**
	 * 平台：TIKTOK / SHOPEE / LAZADA 等
	 */
		@TableField("platform_code")
		private String platformCode;

	/**
	 * 平台 SKU ID（如 TikTok 返回的 sku_id）
	 */
		@TableField("platform_sku_id")
		private String platformSkuId;

	/**
	 * 平台的 seller_sku（店铺自定义）
	 */
		@TableField("seller_sku_code")
		private String sellerSkuCode;

	/**
	 * 内部 SKU 表（sku.id） → 核心映射关系
	 */
		@TableField("sku_id")
		private Long skuId;

	/**
	 * 售价币种（如 PHP）
	 */
		@TableField("currency_code")
		private String currencyCode;

	/**
	 * 当前售价（可选）
	 */
		@TableField("sale_price")
		private BigDecimal salePrice;

	/**
	 * 划线价（可选）
	 */
		@TableField("original_price")
		private BigDecimal originalPrice;

	/**
	 * 平台 SKU 是否上架：1 是 0 否
	 */
		@TableField("is_active")
		private Integer isActive;

	/**
	 * 逻辑删除
	 */
		@TableField("is_deleted")
		private Integer isDeleted;

	/**
	 * 创建时间
	 */
		@TableField("created_time")
		private Date createdTime;

	/**
	 * 更新时间
	 */
		@TableField("updated_time")
		private Date updatedTime;


}
