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
 * 供应商表
 *
 * @author wenbin
 * @email *****@mail.com
 * @date 2025-11-25 20:42:30
 */
@Data
@TableName("supplier")
public class SupplierEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId("id")
	private Long id;

	/**
	 * 供应商名称
	 */
		@TableField("name")
		private String name;

	/**
	 * 供应商名称简称
	 */
		@TableField("sample_name")
		private String sampleName;

	/**
	 * 联系电话
	 */
		@TableField("contact")
		private String contact;

	/**
	 * 微信号
	 */
		@TableField("wechat_id")
		private String wechatId;

	/**
	 * 
	 */
		@TableField("create_date")
		private Date createDate;

	/**
	 * 
	 */
		@TableField("create_by")
		private String createBy;

	/**
	 * 
	 */
		@TableField("update_date")
		private Date updateDate;

	/**
	 * 
	 */
		@TableField("update_by")
		private String updateBy;


}
