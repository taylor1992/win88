package com.company.project.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.company.project.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldFill;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 内部产品表（SPU，所有店铺共用）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-28 09:56:45
 */
@Data
@TableName("product")
public class ProductEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 内部产品主键 ID
	 */
	@TableId("id")
	private Long id;

	/**
	 * 内部产品编码（如 WK-LAP-17-N5095）
	 */
		@TableField("product_code")
		private String productCode;

	/**
	 * 产品名称（内部用，比如：17.3寸N5095办公本）
	 */
		@TableField("product_name")
		private String productName;

	/**
	 * 产品英文名称（可选）
	 */
		@TableField("product_name_en")
		private String productNameEn;

	/**
	 * 品牌名称（如 WK / X ByteBox）
	 */
		@TableField("brand_name")
		private String brandName;

	/**
	 * 内部类目 ID（你自己的类目）
	 */
		@TableField("category_id")
		private Long categoryId;

	/**
	 * 主图（内部展示/仓库识别）
	 */
		@TableField("main_image_url")
		private String mainImageUrl;

	/**
	 * 缩略图（可选）
	 */
		@TableField("thumbnail_image_url")
		private String thumbnailImageUrl;

	/**
	 * 简介（内部描述）
	 */
		@TableField("short_description")
		private String shortDescription;

	/**
	 * 详细描述（规格、卖点等，可选）
	 */
		@TableField("detail_description")
		private String detailDescription;

	/**
	 * 状态：1生效 0下线 2草稿 等（枚举 INT）
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
