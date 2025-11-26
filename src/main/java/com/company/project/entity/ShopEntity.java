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
 * 
 *
 * @author wenbin
 * @email *****@mail.com
 * @date 2025-11-25 21:49:35
 */
@Data
@TableName("shop")
public class ShopEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 店铺ID
	 */
	@TableId("shop_id")
	private String shopId;

	/**
	 * 店铺类型 1-Tiktok 2-Shopee 3-Lazada 4-Facebook 5-其他线下
	 */
		@TableField("type")
		private Integer type;

	/**
	 * 店铺名
	 */
		@TableField("name")
		private String name;

	/**
	 * 注册时间
	 */
		@TableField("register_date")
		private Date registerDate;

	/**
	 * 所属区域
	 */
		@TableField("area")
		private String area;

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

	/**
	 * 
	 */
		@TableField("create_time")
		private Date createTime;

	/**
	 * 
	 */
		@TableField("create_by")
		private String createBy;


}
