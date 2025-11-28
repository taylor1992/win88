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
 * SKU 表：支持多平台、多店铺、变体规格
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-27 23:37:53
 */
@Data
@TableName("sku")
public class SkuEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键 ID
	 */
	@TableId("id")
	private Long id;

	/**
	 * 内部自定义 SKU 编码（如 WK-N95-12+512-BLACK）
	 */
		@TableField("sku_code")
		private String skuCode;

	/**
	 * 关联 SPU 商品表 ID
	 */
		@TableField("product_id")
		private Long productId;

	/**
	 * 第一层规格名
	 */
		@TableField("option_1_name")
		private String option1Name;

	/**
	 * 第一层规格值
	 */
		@TableField("option_1_value")
		private String option1Value;

	/**
	 * 第二层规格名
	 */
		@TableField("option_2_name")
		private String option2Name;

	/**
	 * 第二层规格值
	 */
		@TableField("option_2_value")
		private String option2Value;

	/**
	 * 第三层规格名
	 */
		@TableField("option_3_name")
		private String option3Name;

	/**
	 * 第三层规格值
	 */
		@TableField("option_3_value")
		private String option3Value;

	/**
	 * 第四层规格名
	 */
		@TableField("option_4_name")
		private String option4Name;

	/**
	 * 第四层规格值
	 */
		@TableField("option_4_value")
		private String option4Value;

	/**
	 * 第五层规格名
	 */
		@TableField("option_5_name")
		private String option5Name;

	/**
	 * 第五层规格值
	 */
		@TableField("option_5_value")
		private String option5Value;

	/**
	 * SKU 主图
	 */
		@TableField("image_url")
		private String imageUrl;

	/**
	 * 重量（kg）
	 */
		@TableField("weight_kg")
		private BigDecimal weightKg;

	/**
	 * 长（cm）
	 */
		@TableField("length_cm")
		private BigDecimal lengthCm;

	/**
	 * 宽（cm）
	 */
		@TableField("width_cm")
		private BigDecimal widthCm;

	/**
	 * 高（cm）
	 */
		@TableField("height_cm")
		private BigDecimal heightCm;

	/**
	 * 条码（可选）
	 */
		@TableField("barcode")
		private String barcode;

	/**
	 * 是否启用：1=启用，0=禁用
	 */
		@TableField("is_active")
		private Integer isActive;

	/**
	 * 逻辑删除：1=删除
	 */
		@TableField("is_deleted")
		private Integer isDeleted;

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
