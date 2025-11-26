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
 * 产品
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-26 17:27:36
 */
@Data
@TableName("product")
public class ProductEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 产品ID
	 */
	@TableId("id")
	private String id;

	/**
	 * 产品名称
	 */
		@TableField("name")
		private String name;

	/**
	 * 店铺ID
	 */
		@TableField("shop_id")
		private String shopId;

	/**
	 * 店铺名称
	 */
		@TableField("shop_name")
		private String shopName;

	/**
	 * 店铺类型
	 */
		@TableField("shop_type")
		private Integer shopType;

	/**
	 * 创建时间
	 */
		@TableField("create_time")
		private Date createTime;

	/**
	 * 
	 */
		@TableField("create_by")
		private String createBy;

	/**
	 * 
	 */
		@TableField("update_time")
		private Date updateTime;

	/**
	 * 
	 */
		@TableField("update_by")
		private String updateBy;


}
