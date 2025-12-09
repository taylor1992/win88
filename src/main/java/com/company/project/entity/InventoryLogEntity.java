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
 * 库存变更流水表（支持手工汇总调整，也预留店铺/订单维度）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-04 22:18:16
 */
@Data
@TableName("inventory_log")
public class InventoryLogEntity extends BaseEntity implements Serializable {
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
     * 内部 SKU ID
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 店铺 ID（TikTok 多店铺等）
     */
    @TableField("shop_id")
    private Long shopId;

    /**
     * 平台：TIKTOK / SHOPEE / LAZADA 等
     */
    @TableField("platform_code")
    private String platformCode;

    /**
     * 变化数量：入库=正数，出库=负数
     */
    @TableField("change_qty")
    private Integer changeQty;

    /**
     * 变更类型（枚举 INT）
     */
    @TableField("change_type")
    private Integer changeType;

    /**
     * 关联单据类型（枚举 INT，可为空）
     */
    @TableField("ref_type")
    private Integer refType;

    /**
     * 关联单据 ID（你系统内部主键，可为空）
     */
    @TableField("ref_id")
    private Long refId;

    /**
     * 外部订单号，如 TikTok 订单号（可为空）
     */
    @TableField("ref_order_no")
    private String refOrderNo;

    /**
     * 变更前库存（可选，便于审计）
     */
    @TableField("qty_before")
    private Integer qtyBefore;

    /**
     * 变更后库存（可选）
     */
    @TableField("qty_after")
    private Integer qtyAfter;

    /**
     * 备注，如“2025-11-27 日结调整”等
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


    @TableField(exist = false)
    private Date createdTimeBegin;
    @TableField(exist = false)
    private Date createdTimeEnd;

    /**
     * 更新时间
     */
    @TableField("updated_time")
    private Date updatedTime;


}
