package com.diandian.admin.business.common.constant;

/**
 * @author cjunyuan
 * @date 2018/12/10 18:00
 */
public class MerchantConstant {

    public enum Type{
        AGENT, PLATMERCHANT
    }

    public enum AccountType{
        FUND
    }

    public enum AuditState{
        WAIT_AUDIT(0, "待审核"),
        AUDIT_PASSED(1, "审核通过"),
        AUDIT_NOT_PASSED(2, "审核不通过");
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
}
