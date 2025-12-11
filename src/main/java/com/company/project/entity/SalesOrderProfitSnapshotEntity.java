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
 * 销售订单利润快照表（收入/成本/费用/毛利/净利）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-11 08:52:17
 */
@Data
@TableName("sales_order_profit_snapshot")
public class SalesOrderProfitSnapshotEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键 ID
	 */
	@TableId("id")
	private Long id;

	/**
	 * 销售订单 ID（sales_order.id）
	 */
		@TableField("order_id")
		private Long orderId;

	/**
	 * 平台：TIKTOK / SHOPEE / LAZADA / OFFLINE 等
	 */
		@TableField("platform_code")
		private String platformCode;

	/**
	 * 店铺 ID
	 */
		@TableField("shop_id")
		private Long shopId;

	/**
	 * 平台订单号
	 */
		@TableField("platform_order_no")
		private String platformOrderNo;

	/**
	 * 快照日期（通常取订单完成日或跑批日期）
	 */
		@TableField("snapshot_date")
		private Date snapshotDate;

	/**
	 * 订单币种，如 PHP
	 */
		@TableField("order_currency")
		private String orderCurrency;

	/**
	 * 本位币种，如 PHP
	 */
		@TableField("base_currency")
		private String baseCurrency;

	/**
	 * 1 订单币 = ? 本位币
	 */
		@TableField("fx_rate_order_to_base")
		private BigDecimal fxRateOrderToBase;

	/**
	 * 商品收入（已扣除商家折扣）
	 */
		@TableField("revenue_goods")
		private BigDecimal revenueGoods;

	/**
	 * 向买家收取的运费
	 */
		@TableField("revenue_shipping")
		private BigDecimal revenueShipping;

	/**
	 * 买家承担的税费收入
	 */
		@TableField("revenue_tax")
		private BigDecimal revenueTax;

	/**
	 * 计入商家侧的平台官网补贴（增加收入）
	 */
		@TableField("platform_subsidy_inc")
		private BigDecimal platformSubsidyInc;

	/**
	 * 商品成本（采购成本按规则汇总）
	 */
		@TableField("cost_goods")
		private BigDecimal costGoods;

	/**
	 * 商家承担运费（国际+本地物流分摊到此单）
	 */
		@TableField("cost_shipping")
		private BigDecimal costShipping;

	/**
	 * 其他成本分摊（包装材料、仓储等）
	 */
		@TableField("cost_other_alloc")
		private BigDecimal costOtherAlloc;

	/**
	 * 平台佣金/技术服务费
	 */
		@TableField("fee_commission")
		private BigDecimal feeCommission;

	/**
	 * 支付渠道手续费
	 */
		@TableField("fee_payment")
		private BigDecimal feePayment;

	/**
	 * 联盟/达人佣金
	 */
		@TableField("fee_affiliate")
		private BigDecimal feeAffiliate;

	/**
	 * 其他平台费用
	 */
		@TableField("fee_other")
		private BigDecimal feeOther;

	/**
	 * 订单币种总收入 = 上面所有收入项之和
	 */
		@TableField("revenue_total_order")
		private BigDecimal revenueTotalOrder;

	/**
	 * 订单币种总成本 = cost_goods + cost_shipping + cost_other_alloc
	 */
		@TableField("cost_total_order")
		private BigDecimal costTotalOrder;

	/**
	 * 订单币种总平台费用 = fee_commission + fee_payment + fee_affiliate + fee_other
	 */
		@TableField("fee_total_order")
		private BigDecimal feeTotalOrder;

	/**
	 * 订单币种毛利 = revenue_total_order - cost_total_order
	 */
		@TableField("gross_profit_order")
		private BigDecimal grossProfitOrder;

	/**
	 * 订单币种净利 = gross_profit_order - fee_total_order
	 */
		@TableField("net_profit_order")
		private BigDecimal netProfitOrder;

	/**
	 * 本位币总收入
	 */
		@TableField("revenue_total_base")
		private BigDecimal revenueTotalBase;

	/**
	 * 本位币总成本
	 */
		@TableField("cost_total_base")
		private BigDecimal costTotalBase;

	/**
	 * 本位币总平台费用
	 */
		@TableField("fee_total_base")
		private BigDecimal feeTotalBase;

	/**
	 * 本位币毛利
	 */
		@TableField("gross_profit_base")
		private BigDecimal grossProfitBase;

	/**
	 * 本位币净利
	 */
		@TableField("net_profit_base")
		private BigDecimal netProfitBase;

	/**
	 * 计算版本（预留，算法调整时区分批次）
	 */
		@TableField("calc_version")
		private String calcVersion;

	/**
	 * 备注（异常订单说明等）
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
