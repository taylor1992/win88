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
 * 汇率表（支持多币种、按日期生效）
 *
 * @author taylor
 * @email taylor@gmail.com
 * @date 2025-11-26 20:32:53
 */
@Data
@TableName("currency_rate")
public class CurrencyRateEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("id")
    private Long id;

    /**
     * 原始币种，固定为 CNY
     */
    @TableField("from_currency")
    private String fromCurrency;

    /**
     * 目标币种：PHP / USD 等
     */
    @TableField("to_currency")
    private String toCurrency;

    /**
     * 汇率：1 from_currency = rate to_currency
     */
    @TableField("rate")
    private BigDecimal rate;

    /**
     * 汇率日期（生效日期）
     */
    @TableField("rate_date")
    private Date rateDate;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;

}
