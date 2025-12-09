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
 * 当前库存表（仓库 + 内部 SKU）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-03 23:23:49
 */
@Data
@TableName("inventory")
public class InventoryEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键 ID
	 */
	@TableId("id")
	private Long id;

	/**
	 * 仓库 ID
	 */
		@TableField("warehouse_id")
		private Long warehouseId;

	/**
	 * 内部 SKU ID（关联 sku.id）
	 */
		@TableField("sku_id")
		private Long skuId;

	/**
	 * 当前在库数量（实物）
	 */
		@TableField("qty_on_hand")
		private Integer qtyOnHand;

	/**
	 * 占用库存（已下单未发货）
	 */
		@TableField("qty_reserved")
		private Integer qtyReserved;

	/**
	 * 可售库存 = qty_on_hand - qty_reserved
	 */
		@TableField("qty_available")
		private Integer qtyAvailable;

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
