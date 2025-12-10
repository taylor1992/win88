package com.company.project.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
 * 支出明细表
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-12-10 22:20:23
 */
@Data
@TableName("expense")
public class ExpenseEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @TableId("id")
    private Long id;

    /**
     * 支出日期（发生日期）
     */
    @JSONField(format = "yyyy-MM-dd")
    @TableField("expense_date")
    private Date expenseDate;

    @TableField(exist = false)
    private Date expenseDateBegin;
    @TableField(exist = false)
    private Date expenseDateEnd;

    /**
     * 支出类型：1-工资，2-房租，3-水电网，4-日常开销，5-物流费用，6-平台费用，7-采购杂费，8-资产购置，9-税费，99-其他
     */
    @TableField("category")
    private Integer category;

    /**
     * 支出金额（原币种金额）
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 币种，如 PHP / CNY / USD。若只用一种币种也可以以后去掉
     */
    @TableField("currency")
    private String currency;

    /**
     * 业务标签：如办公室租金、仓库租金、某个店铺、某个项目等
     */
    @TableField("biz_tag")
    private String bizTag;

    /**
     * 关联订单号/采购单号/合同号，可为空
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 备注说明
     */
    @TableField("remark")
    private String remark;

    /**
     * 状态：1-已确认，0-草稿，2-作废
     */
    @TableField("status")
    private Integer status;

    /**
     * 逻辑删除 0-正常 1-删除
     */
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    private Integer deleted;

    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;

    /**
     * 更新人
     */
    @TableField("updated_by")
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField("updated_time")
    private Date updatedTime;


}
