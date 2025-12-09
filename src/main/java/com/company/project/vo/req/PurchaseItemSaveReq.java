package com.company.project.vo.req;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 保存采购单明细的请求体
 */
@Data
public class PurchaseItemSaveReq {

    /** 采购单 ID */
    private Long purchaseOrderId;

    /** 明细列表 */
    private List<ItemDTO> items;

    @Data
    public static class ItemDTO {
        private Long id;
        private Long skuId;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal amountFreightAlloc;
        private BigDecimal amountOtherAlloc;
        private BigDecimal weightKgPerUnit;
        private String remark;
    }
}
