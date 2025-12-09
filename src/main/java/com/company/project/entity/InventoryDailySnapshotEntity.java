package com.company.project.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.company.project.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldFill;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 每日库存快照表（EOD 日结库存，用于对账与审计）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-09 22:02:07
 */
@Data
@TableName("inventory_daily_snapshot")
public class InventoryDailySnapshotEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @TableId("id")
    private Long id;

    /**
     * 快照日期（每天一条）
     */
    @JSONField(format = "yyyy-MM-dd")
    @TableField("snapshot_date")
    private Date snapshotDate;
    @TableField(exist = false)
    private Date snapshotDateBegin;
    @TableField(exist = false)
    private Date snapshotDateEnd;

    /**
     *
     */
    @TableField("warehouse_id")
    private Long warehouseId;

    /**
     *
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 该日结束时的实物库存
     */
    @TableField("qty_on_hand")
    private Integer qtyOnHand;

    /**
     * 该日结束时的占用库存
     */
    @TableField("qty_reserved")
    private Integer qtyReserved;

    /**
     * 该日结束时的可售库存
     */
    @TableField("qty_available")
    private Integer qtyAvailable;

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
     * 快照生成时间
     */
    @TableField("created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @TableField("updated_time")
    private Date updatedTime;





}
