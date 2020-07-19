package com.diandian.admin.common.constant;

public class BisinessConstant {

    public enum AuditState{
        WAIT(0, "待审核"),
        SUCCESS(1, "审核通过"),
        FAIL(2, "审核失败");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;
        AuditState(Integer value, String label){
            this.value = value;
            this.lable = lable;
        }

        public Integer value() {
            return this.value;
        }

        public String getLable() {
            return this.lable;
        }
    }

    public enum PaymentState{
        WAIT(0, "待付款"),
        SUCCESS(0, "付款成功"),
        FAIL(1, "付款失败");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;
        PaymentState(Integer value, String label){
            this.value = value;
            this.lable = lable;
        }

        public Integer value() {
            return this.value;
        }

        public String getLable() {
            return this.lable;
        }
    }

}
