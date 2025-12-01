package com.company.project.common.enums;

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
}
