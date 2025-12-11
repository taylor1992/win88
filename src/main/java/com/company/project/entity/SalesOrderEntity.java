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
 * 销售订单主表（精简，利润计算用）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-11 08:48:50
 */
@Data
@TableName("sales_order")
public class SalesOrderEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @TableId("id")
    private Long id;

    /**
     * 平台：TIKTOK / SHOPEE / LAZADA / OFFLINE 等
     */
    @TableField("platform_code")
    private String platformCode;

    /**
     * 店铺 ID（你系统内部店铺）
     */
    @TableField("shop_id")
    private Long shopId;

    /**
     * 平台订单号
     */
    @TableField("platform_order_no")
    private String platformOrderNo;

    /**
     * 订单状态：0-待付款，1-已付款，2-已发货，3-已完成，4-已取消，5-投递失败/退货完结
     */
    @TableField("order_status")
    private Integer orderStatus;

    /**
     * 订单币种，如 PHP/CNY/USD
     */
    @TableField("currency_code")
    private String currencyCode;

    /**
     * 商品成交总额（未扣减任何优惠）
     */
    @TableField("goods_amount")
    private BigDecimal goodsAmount;

    /**
     * 向买家收取的运费
     */
    @TableField("shipping_fee_buyer")
    private BigDecimal shippingFeeBuyer;

    /**
     * 买家承担的税费（VAT 等）
     */
    @TableField("tax_amount")
    private BigDecimal taxAmount;

    /**
     * 商家出的钱：商家券/店铺折扣总额（正数表示减收入）
     */
    @TableField("seller_discount_total")
    private BigDecimal sellerDiscountTotal;

    /**
     * 平台券/平台补贴（由平台承担）
     */
    @TableField("platform_discount_total")
    private BigDecimal platformDiscountTotal;

    /**
     * 买家实付金额（平台实际向买家收的钱）
     */
    @TableField("buyer_pay_amount")
    private BigDecimal buyerPayAmount;

    /**
     * 下单时间
     */
    @TableField("order_time")
    private Date orderTime;

    @TableField(exist = false)
    private Date orderTimeBegin;

    @TableField(exist = false)
    private Date orderTimeEnd;

    /**
     * 支付时间
     */
    @TableField("pay_time")
    private Date payTime;

    /**
     * 订单完成时间（用于统计维度）
     */
    @TableField("complete_time")
    private Date completeTime;

    /**
     * 逻辑删除 0-正常 1-删除
     */
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    private Integer deleted;

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

    @TableField("tracking_id")
    private String trackingId;

    @TableField("shipping_provider")
    private String shippingProvider;
}
