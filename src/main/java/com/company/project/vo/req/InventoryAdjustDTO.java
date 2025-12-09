package com.company.project.vo.req;

import lombok.Data;

@Data
public class InventoryAdjustDTO {
    private Long warehouseId;
    private Long skuId;
    private Integer changeQty; // 正数=入库，负数=出库
    private String remark;
    private Integer changeType;
    private String updatedBy;
}

