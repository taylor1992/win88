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
 * 销售订单结算明细表（精简，利润计算用）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-11 08:51:21
 */
@Data
@TableName("sales_order_settlement")
public class SalesOrderSettlementEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键 ID
	 */
	@TableId("id")
	private Long id;

	/**
	 * 平台
	 */
		@TableField("platform_code")
		private String platformCode;

	/**
	 * 店铺 ID
	 */
		@TableField("shop_id")
		private Long shopId;

	/**
	 * 关联销售订单 ID（sales_order.id）
	 */
		@TableField("order_id")
		private Long orderId;

	/**
	 * 平台订单号
	 */
		@TableField("platform_order_no")
		private String platformOrderNo;

	/**
	 * 结算日期（平台账单生效日）
	 */
		@TableField("settlement_date")
		private Date settlementDate;

	/**
	 * 结算币种
	 */
		@TableField("currency_code")
		private String currencyCode;

	/**
	 * 买家支付商品金额
	 */
		@TableField("buyer_pay_goods")
		private BigDecimal buyerPayGoods;

	/**
	 * 买家支付运费
	 */
		@TableField("buyer_pay_shipping")
		private BigDecimal buyerPayShipping;

	/**
	 * 买家支付税费
	 */
		@TableField("buyer_pay_tax")
		private BigDecimal buyerPayTax;

	/**
	 * 平台补贴计入商家侧的金额
	 */
		@TableField("platform_subsidy")
		private BigDecimal platformSubsidy;

	/**
	 * 商家承担的优惠（正数表示减少收入）
	 */
		@TableField("seller_voucher_used")
		private BigDecimal sellerVoucherUsed;

	/**
	 * 平台承担的优惠（仅用于对账分析，可选）
	 */
		@TableField("platform_voucher_used")
		private BigDecimal platformVoucherUsed;

	/**
	 * 平台佣金/技术服务费
	 */
		@TableField("commission_fee")
		private BigDecimal commissionFee;

	/**
	 * 支付手续费
	 */
		@TableField("payment_fee")
		private BigDecimal paymentFee;

	/**
	 * 商家承担的运费（平台从结算中扣除部分）
	 */
		@TableField("shipping_fee_seller")
		private BigDecimal shippingFeeSeller;

	/**
	 * 联盟/达人佣金
	 */
		@TableField("affiliate_commission")
		private BigDecimal affiliateCommission;

	/**
	 * 其他费用（无法细分部分）
	 */
		@TableField("other_fee")
		private BigDecimal otherFee;

	/**
	 * 平台最终打给商家的净入账金额
	 */
		@TableField("net_payout_amount")
		private BigDecimal netPayoutAmount;

	/**
	 * 导入源文件名
	 */
		@TableField("source_file_name")
		private String sourceFileName;

	/**
	 * 源文件行号
	 */
		@TableField("source_row_no")
		private Integer sourceRowNo;

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
