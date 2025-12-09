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
 * 采购单明细表（多币种，含费用分摊）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-02 22:08:01
 */
@Data
@TableName("purchase_order_item")
public class PurchaseOrderItemEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId("id")
	private Long id;

	/**
	 * 关联采购单
	 */
		@TableField("purchase_order_id")
		private Long purchaseOrderId;

	/**
	 * SKU ID
	 */
		@TableField("sku_id")
		private Long skuId;

	/**
	 * 采购数量
	 */
		@TableField("quantity")
		private Integer quantity;

	/**
	 * 单价（不含运费），单位=主表 currency_code
	 */
		@TableField("unit_price")
		private BigDecimal unitPrice;

	/**
	 * 商品小计 = unit_price * quantity
	 */
		@TableField("amount_goods_subtotal")
		private BigDecimal amountGoodsSubtotal;

	/**
	 * 单件重量 kg（可选）
	 */
		@TableField("weight_kg_per_unit")
		private BigDecimal weightKgPerUnit;

	/**
	 * 本行总重量 kg
	 */
		@TableField("weight_kg_total")
		private BigDecimal weightKgTotal;

	/**
	 * 分摊到本行的运费
	 */
		@TableField("amount_freight_alloc")
		private BigDecimal amountFreightAlloc;

	/**
	 * 分摊到本行的其他费用
	 */
		@TableField("amount_other_alloc")
		private BigDecimal amountOtherAlloc;

	/**
	 * 本行总成本 = goods + freight + other，单位=currency_code
	 */
		@TableField("amount_cost_total")
		private BigDecimal amountCostTotal;

	/**
	 * 
	 */
		@TableField("remark")
		private String remark;

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
