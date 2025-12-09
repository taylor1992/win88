package com.company.project.vo.resp;

import lombok.Data;

import java.util.Date;

@Data
public class InventoryVO {
    private Long id;
    private Long warehouseId;
    private String warehouseName;
    private Long skuId;
    private String skuCode;
    private String skuName;
    private Integer qtyOnHand;
    private Integer qtyReserved;
    private Integer qtyAvailable;
    private Date updatedTime;
}

