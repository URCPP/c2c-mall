package com.diandian.dubbo.facade.common.enums;

/**
 * @author cjunyuan
 * @date 2018/12/10 18:00
 */
public class MerchantConstant {

    public enum Type {
        AGENT, PLATMERCHANT
    }

    public enum AccountType {
        FUND
    }

    public enum AuditState {
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

        AuditState(Integer value, String label) {
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

    /**
     * 软件申请类型
     */
    public enum SoftApplyType {
        OPEN(0, "开通"),
        RENEW(1, "续费");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        SoftApplyType(Integer value, String label) {
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

    /**
     * 商户认证状态
     */
    public enum MerchantApproveState {
        APPROVED(2, "己认证"),
        APPROVE_FAIL(3, "认证失败"),
        APPROVEING(1, "认证中"),
        UNAPPROVE(0, "未认证");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        MerchantApproveState(Integer value, String label) {
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

    //商城开通金额
    public final static String MALL_OPEN_AMOUNT = "mall_open_amount";
    //保证金
    public final static String MARGIN_AMOUNT = "margin_amount";

    /**
     * 商户钱包变更记录类型
     */
    public enum MerchantWalletLogType {
        STORED(1, "充值"),
        EXCHANGE_PRODUCT(0, "兑换商品"),
        REFUND(3, "储备金退还"),
        MARGIN(2, "保证金");


        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        MerchantWalletLogType(Integer value, String label) {
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

    /**
     * 资金变动类型
     */
    public enum TypeFlag {
        STORED(0, "充值"),
        RENEW(1, "续费"),
        PROCUREMENT(2, "采购"),
        WITHDRAW(3, "提现"),
        REFUND(4, "退款"),
        RECOMMEND(5, "推荐奖励"),
        UNDO_APPLY(6, "撤消申请奖励");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        TypeFlag(Integer value, String label) {
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

    /**
     * 商户是否过期标志
     */
    public enum IsExpireFlag {
        NORMAL(0, "未过期"),
        EXPIRE(1, "过期");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        IsExpireFlag(Integer value, String label) {
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

    /**
     * 商户自定义商城开通状态( 0 未开通 ， 1 开通, 2审核中)
     */
    public enum MallOpenFlag {
        CLOSE(0, "未开通"),
        AUDIT(2, "开通审核中"),
        BACK_AUDIT(3, "退还审核中"),
        OPEN(1, "开通");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        MallOpenFlag(Integer value, String label) {
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

    /**
     * 商户支付费用交易状态(0待支付 1交易成功 2交易失败)
     */
    public enum MerchantPayState {
        WAIT(0, "待支付"),
        SUCCESS(1, "交易成功"),
        FAIL(2, "交易失败");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        MerchantPayState(Integer value, String label) {
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

    /**
     * 商户支付费用类型(0充值 1续费)
     */
    public enum MerchantPayType {
        RECHARGE(0, "商户充值"),
        RENEW(1, "商户续费"),
        OPEN_MALL(2, "商户开通商城"),
        OPENING_COST(3, "商户开通"),
        SECURITY_DEPOSIT(4,"商户缴纳质保金");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String label;

        MerchantPayType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer value() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }

    /**
     * @Author wubc
     * @Description // 商户打款类型
     * @Date 21:14 2019/3/29
     * @Param
     * @return
     **/
    public enum MerchantRemitType {
        WALLET(0, "储备金充值打款"),
        SEND_BACK(2, "储备金退还"),
        OPEN_MALL(1, "开通商城打款");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        MerchantRemitType(Integer value, String label) {
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

    /**
     * @Author wubc
     * @Description // 线下支付审核状态
     * @Date 9:53 2019/4/1
     * @Param
     * @return
     **/
    public enum RemitAuditState {
        CREATE(0,"创建"),
        WAIT_AUDIT(1, "待审核"),
        AUDIT_PASSED(2, "审核通过"),
        AUDIT_CANCEL(4, "审核取消"),
        AUDIT_NOT_PASSED(3, "审核不通过");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        RemitAuditState(Integer value, String label) {
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

    /**
     * @Author wubc
     * @Description // 商户帐户变动类型
     * @Date 9:54 2019/4/1
     * @Param
     * @return
     **/
    public enum AccountHistoryType {
        CHARGE(0,"充值"),
        RENEW(1, "续费"),
        PURCHASE(2, "采购"),
        REFUND(4, "退款"),
        RECOMMEND(5, "推荐奖励"),
        WITH_DARW(3, "提现");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        AccountHistoryType(Integer value, String label) {
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

    /**
     * @Author wubc
     * @Description // 商家开通类型
     * @Date 11:26 2019/4/4
     * @Param
     * @return
     **/
    public enum OpenType {
        PAY_OPEN(0,"付费"),
        FREE_OPEN(1, "免费");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        OpenType(Integer value, String label) {
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


    /**
     * @Author wubc
     * @Description  商户运费设置
     * @Date 10:32 2019/4/7
     * @Param
     * @return
     **/
    public enum FreightSet {
        MEMBER(0,"会员"),
        MERCHANT(1, "商家");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        FreightSet(Integer value, String label) {
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

    /**
     * 微信支付交易业务类型
     */
    public enum WxPayBusinessType {
        PRODUCT_ORDER_PAYMENT(0, "商品订单支付"),
        MERCHANT_PAYMENT(1, "商户充值/续费"),
        MERCHANT_PAY_OPENING_COST(2, "商户支付开通费用"),
        ORDER_TRANSPORT_FEE_PAYMENT(3, "积分订单运费支付"),;
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String label;

        WxPayBusinessType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer value() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }

    /**
     * 商户来源
     */
    public enum MerchantSource {
        SYS_DEFAULT(0, "系统默认"),
        DIANDIAN_TO_HOME(1, "点点到家");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String label;

        MerchantSource(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer value() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }

}
