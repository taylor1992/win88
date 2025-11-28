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
 * 采购单主表（多币种·费用分摊·跨国物流·海运空运单号）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-28 22:20:48
 */
@Data
@TableName("purchase_order")
public class PurchaseOrderEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键 ID
	 */
	@TableId("id")
	private Long id;

	/**
	 * 采购单号
	 */
		@TableField("purchase_order_no")
		private String purchaseOrderNo;

	/**
	 * 供应商 ID
	 */
		@TableField("supplier_id")
		private Long supplierId;

	/**
	 * 采购币种，如 CNY/USD
	 */
		@TableField("currency_code")
		private String currencyCode;

	/**
	 * 汇率对应的日期，用于后续成本换算
	 */
		@TableField("fx_rate_date")
		private Date fxRateDate;

	/**
	 * 商品金额合计（不含运费）
	 */
		@TableField("amount_goods_total")
		private BigDecimal amountGoodsTotal;

	/**
	 * 整单运费
	 */
		@TableField("amount_freight_total")
		private BigDecimal amountFreightTotal;

	/**
	 * 整单其他费用（包材、清关费等）
	 */
		@TableField("amount_other_total")
		private BigDecimal amountOtherTotal;

	/**
	 * 分摊方式：QTY/WEIGHT
	 */
		@TableField("allocation_method")
		private String allocationMethod;

	/**
	 * 物流方式：AIR 空运 / SEA 海运
	 */
		@TableField("logistics_method")
		private String logisticsMethod;

	/**
	 * 物流单号（海运/空运）
	 */
		@TableField("logistics_tracking_no")
		private String logisticsTrackingNo;

	/**
	 * 中国出库/发货时间
	 */
		@TableField("ship_out_time")
		private Date shipOutTime;

	/**
	 * 菲律宾仓库入库时间
	 */
		@TableField("warehouse_in_time")
		private Date warehouseInTime;

	/**
	 * 采购状态：0草稿 1已提交 2运输中 3部分入库 4已入库
	 */
		@TableField("status")
		private Integer status;

	/**
	 * 备注
	 */
		@TableField("remark")
		private String remark;

	/**
	 * 创建时间
	 */
		@TableField("created_time")
		private Date createdTime;

	/**
	 * 创建人
	 */
		@TableField("created_by")
		private String createdBy;

	/**
	 * 更新时间
	 */
		@TableField("updated_time")
		private Date updatedTime;

	/**
	 * 更新人
	 */
		@TableField("updated_by")
		private String updatedBy;


}
