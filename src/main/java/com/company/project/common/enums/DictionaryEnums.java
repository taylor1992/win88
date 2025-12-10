package com.company.project.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DictionaryEnums {

    /** ================= 店铺类型 ================= */
    public enum ShopTypeEnum {
        TIKTOK(1, "Tiktok"),
        SHOPEE(2, "Shopee"),
        LAZADA(3, "Lazada"),
        FACEBOOK(4, "Facebook"),
        OTHER(5, "Other");

        private final int code;
        private final String label;

        ShopTypeEnum(int code, String label) {
            this.code = code;
            this.label = label;
        }
        public int getCode() { return code; }
        public String getLabel() { return label; }
    }

    /** ================= 启用状态 ================= */
    public enum EnableEnum {
        DISABLE(0, "Disable"),
        ENABLE(1, "Enable");

        private final int code;
        private final String label;

        EnableEnum(int code, String label) {
            this.code = code;
            this.label = label;
        }
        public int getCode() { return code; }
        public String getLabel() { return label; }
    }

    /** ================= 商品状态 ================= */
    public enum ProductStatusEnum {
        OFFLINE(0, "Offline"),
        ONLINE(1, "Online"),
        DRAFT(2, "Draft");

        private final int code;
        private final String label;

        ProductStatusEnum(int code, String label) {
            this.code = code;
            this.label = label;
        }
        public int getCode() { return code; }
        public String getLabel() { return label; }
    }

    /** ================= 是否激活 ================= */
    public enum ActiveEnum {
        NO(0, "否"),
        YES(1, "是");

        private final int code;
        private final String label;

        ActiveEnum(int code, String label) {
            this.code = code;
            this.label = label;
        }
        public int getCode() { return code; }
        public String getLabel() { return label; }
    }

    public enum WarehouseEnum {
        LAS_PINAS(1, "Las Piñas,Manila");

        private final int code;
        private final String label;

        WarehouseEnum(int code, String label) {
            this.code = code;
            this.label = label;
        }
        public int getCode() { return code; }
        public String getLabel() { return label; }
    }

    public enum PurchaseStatusEnum {
        DRAFT(0, "Draft"),
        SUBMIT(1, "Submit"),
        SHIPPING(2, "Shipping"),
        PART_IN(3, "Part In"),
        ALL_IN(4, "All In"),
        ;

        private final int code;
        private final String label;
        PurchaseStatusEnum(int code, String label) {
            this.code = code;
            this.label = label;
        }
        public int getCode() { return code; }
        public String getLabel() { return label; }
    }

    @AllArgsConstructor
    @Getter
    public enum InventoryChangeType {

        PURCHASE_IN(1, "采购入库","Purchase in"),
        SALES_OUT(2, "销售出库","Sales out"),
        MANUAL_ADJUST(3, "手工调整","Manual adjust"),
        STOCK_TAKE(4, "盘点调整","Stock take"),
        INITIALIZE(5, "初始化库存","Initialize"),

        // 可选扩展
        ORDER_RESERVE(6, "订单占用库存","Order reserve"),
        ORDER_RELEASE(7, "订单释放库存","Order release"),
        TRANSFER_IN(8, "调拨入库","Transfer in"),
        TRANSFER_OUT(9, "调拨出库","Transfer out"),
        RETURN_IN(10, "退货入库","Return in"),
        DAMAGED_OUT(11, "报损出库","Damaged out"),;

        private final int code;
        private final String desc;
        private final String descEn;

        public static InventoryChangeType fromCode(Integer code) {
            for (InventoryChangeType t : values()) {
                if (t.code == code) return t;
            }
            return null;
        }
    }

    public enum CurrencyEnum {
        CNY("CNY", "CNY"),
        PHP("PHP", "PHP"),
        USD("USD", "USD"),
        ;

        private final String code;
        private final String label;
        CurrencyEnum(String code, String label) {
            this.code = code;
            this.label = label;
        }
        public String getCode() { return code; }
        public String getLabel() { return label; }
    }

    public enum LogisticsMethodEnum {
        AIR("AIR", "AIR"),
        SEA("SEA", "SEA"),
        ;

        private final String code;
        private final String label;
        LogisticsMethodEnum(String code, String label) {
            this.code = code;
            this.label = label;
        }
        public String getCode() { return code; }
        public String getLabel() { return label; }
    }

    public enum AllocationMethodEnum {
        QTY("QTY", "QTY"),
        WEIGHT("WEIGHT", "WEIGHT"),
        ;

        private final String code;
        private final String label;
        AllocationMethodEnum(String code, String label) {
            this.code = code;
            this.label = label;
        }
        public String getCode() { return code; }
        public String getLabel() { return label; }
    }

    public enum ExpenseCategory {

        SALARY(1, "Salary"),               // 工资支出
        RENT(2, "Rent"),                   // 房租支出
        UTILITIES(3, "Utilities"),         // 水电网
        DAILY_EXPENSE(4, "Daily Expense"), // 日常开销（办公用品、杂项）
        FREIGHT(5, "Freight / Delivery"),  // 物流费用（Lalamove、J&T 等）
        PLATFORM_FEE(6, "Platform Fee"),   // 平台费用（佣金、广告）
        PROCUREMENT_FEE(7, "Procurement Fee"), // 采购杂费（税费、服务费等）
        ASSET(8, "Asset Purchase"),        // 固定资产（电脑、办公设备）
        TAX(9, "Tax"),                     // 各类税费
        OTHER(99, "Other");                // 其他类支出

        private final int code;
        private final String label;

        ExpenseCategory(int code, String label) {
            this.code = code;
            this.label = label;
        }

        public int getCode() { return code; }
        public String getLabel() { return label; }

        public static ExpenseCategory fromCode(int code) {
            for (ExpenseCategory c : values()) {
                if (c.code == code) return c;
            }
            return OTHER;
        }
    }

    public enum ExpenseStatus {

        DRAFT(0, "Draft"),               // 工资支出
        CONFIRMED(1, "Confirmed"),                   // 房租支出
        VOIDED(2, "Voided");                // 其他类支出

        private final int code;
        private final String label;

        ExpenseStatus(int code, String label) {
            this.code = code;
            this.label = label;
        }

        public int getCode() { return code; }
        public String getLabel() { return label; }

        public static ExpenseStatus fromCode(int code) {
            for (ExpenseStatus c : values()) {
                if (c.code == code) return c;
            }
            return null;
        }
    }

}
