package com.company.project.vo.req;

import lombok.Data;

@Data
public class InventoryQueryParam {
    private Long warehouseId;
    private String skuKeyword;
    private Boolean onlyLack; // true=可售<=0
    private Integer page;
    private Integer limit;
}

