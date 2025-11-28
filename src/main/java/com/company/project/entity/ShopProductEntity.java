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
 * 店铺产品表（平台商品 Listing，关联内部产品）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-28 13:11:18
 */
@Data
@TableName("shop_product")
public class ShopProductEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 店铺产品主键 ID
	 */
	@TableId("id")
	private Long id;

    @TableField(exist = false)
    private String shopName;

	/**
	 * 店铺 ID（TikTok 店 / Shopee 店等）
	 */
		@TableField("shop_id")
		private String shopId;

	/**
	 * 平台：TIKTOK / SHOPEE / LAZADA 等
	 */
		@TableField("platform_code")
		private String platformCode;

	/**
	 * 内部产品 ID（关联 product.id）
	 */
		@TableField("product_id")
		private Long productId;

        @TableField(exist = false)
        private String productName;

	/**
	 * 平台产品 ID（如 TikTok 返回的 product_id）
	 */
		@TableField("platform_product_id")
		private String platformProductId;

	/**
	 * 该店铺该平台展示的商品标题
	 */
		@TableField("title")
		private String title;

	/**
	 * 副标题/短描述（可选）
	 */
		@TableField("sub_title")
		private String subTitle;

	/**
	 * 店铺商品主图（可以与内部不同）
	 */
		@TableField("main_image_url")
		private String mainImageUrl;

	/**
	 * 平台商品详情链接（可选）
	 */
		@TableField("detail_url")
		private String detailUrl;

	/**
	 * 平台类目 ID（如 TikTok 类目）
	 */
		@TableField("platform_category_id")
		private String platformCategoryId;

	/**
	 * 关键词/标签（逗号分隔，可选）
	 */
		@TableField("tags")
		private String tags;

	/**
	 * 状态：1上架 0下架 2草稿（枚举 INT）
	 */
		@TableField("status")
		private Integer status;

	/**
	 * 备注
	 */
		@TableField("remark")
		private String remark;

	/**
	 * 创建人
	 */
		@TableField("created_by")
		private String createdBy;

	/**
	 * 更新人
	 */
		@TableField("updated_by")
		private String updatedBy;

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
