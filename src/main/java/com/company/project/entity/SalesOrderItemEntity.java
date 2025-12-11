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
 * 销售订单明细表（精简，SKU 级金额）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-11 20:05:27
 */
@Data
@TableName("sales_order_item")
public class SalesOrderItemEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @TableId("id")
    private Long id;

    /**
     * 关联销售订单 ID（sales_order.id）
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 平台，冗余，方便查询
     */
    @TableField("platform_code")
    private String platformCode;

    /**
     * 店铺 ID，冗余
     */
    @TableField("shop_id")
    private Long shopId;

    /**
     * 平台订单号，冗余
     */
    @TableField("platform_order_no")
    private String platformOrderNo;

    /**
     * 内部 SKU ID（你系统中的 sku 主键）
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 店铺 SKU（seller sku，可选）
     */
    @TableField("shop_sku_code")
    private String shopSkuCode;

    /**
     * 平台 SKU/variation 编码（可选）
     */
    @TableField("platform_sku_code")
    private String platformSkuCode;

    /**
     * 商品名称快照（方便人工看，不参与计算）
     */
    @TableField("product_name")
    private String productName;

    /**
     * 成交数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 币种（与订单一致）
     */
    @TableField("currency_code")
    private String currencyCode;

    @TableField("variation")
    private String variation;

    /**
     * 标价单价（展示用）
     */
    @TableField("list_price")
    private BigDecimal listPrice;

    /**
     * 成交单价（折扣前或平台导出的成交价）
     */
    @TableField("sale_price")
    private BigDecimal salePrice;

    /**
     * 行商品金额 = sale_price * quantity
     */
    @TableField("line_amount")
    private BigDecimal lineAmount;

    /**
     * 分摊到本行的商家折扣
     */
    @TableField("seller_discount_alloc")
    private BigDecimal sellerDiscountAlloc;

    /**
     * 分摊到本行的平台折扣/补贴
     */
    @TableField("platform_discount_alloc")
    private BigDecimal platformDiscountAlloc;

    /**
     * 向买家收取运费的行分摊值
     */
    @TableField("shipping_fee_alloc")
    private BigDecimal shippingFeeAlloc;

    /**
     * 税费分摊
     */
    @TableField("tax_amount_alloc")
    private BigDecimal taxAmountAlloc;

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
