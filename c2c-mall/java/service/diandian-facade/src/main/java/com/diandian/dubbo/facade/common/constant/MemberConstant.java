package com.diandian.dubbo.facade.common.constant;

/**
 * 会员常量
 *
 * @author x
 * @date 2018/9/12 16:13
 */
public class MemberConstant {

    /**
     * @Author wubc
     * @Description // 启用
     * @Date 11:22 2019/3/13
     * @Param
     * @return
     **/
    public static final Integer NORMAL = 0;

    /**
     * @Author wubc
     * @Description // 禁用
     * @Date 11:22 2019/3/13
     * @Param
     * @return
     **/
    public static final Integer DISABLE = 1;

    /**
     * 兑换券订单类型(0 商家兑换;)
     */
    public enum MemberExchangeType {

        /**
         * 商家兑换
         */
        MERCHANT_EXCHANGE(0, "兑换券充值"),
        MEMBER_STORED(1, "会员储值赠送"),
        MEMBER_INTEGRAL_EXCHANGE(2, "积分兑换");

        private Integer value;

        private String label;

        MemberExchangeType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * 会员兑换状态
     */
    public enum MemberExchangeState {

        /**
         * 商家兑换
         */
        EXCHANGE_SUCCESS(0, "兑换成功");

        private Integer value;

        private String label;

        MemberExchangeState(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }


    /**
     * 订单操作类型
     */
    public enum BillType {

        /**
         * 商家兑换
         */
        MEMBER_STORED(0, "会员储值"),
        MALL_EXCHANGE(2, "积分兑换"),
        COUPON_EXCHANGE(1, "兑换券充值");

        private Integer value;

        private String label;

        BillType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * 订单状态
     */
    public enum BillState {

        /**
         * 商家兑换
         */
        BILL_CONTINUE(0, "进行中"),
        BILL_SUCCESS(1, "操作成功");

        private Integer value;

        private String label;

        BillState(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    public enum ExchangeHistoryType {
        ISSUE(0, "发放"),
        EXPEND(1, "消耗");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        ExchangeHistoryType(Integer value, String label) {
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
     * 订单状态
     */
    public enum OrderState {

        /**
         * 商家兑换
         */
        ORDER_CREATE(0, "已创建"),
        ORDER_PAID_FREIGHT(1, "已付运费"),
        ORDER_PAID_COUPON(2, "已扣会员兑换券"),
        ORDER_PAID_AMOUNT(3, "已扣商户储备金");
        private Integer value;

        private String label;

        OrderState(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }
    /**
     * 运费承担(0 会员, 1 商家)
     */
    public enum AssumeFreight {

        MEMBER(0, "会员"),
        MERCHANT(1, "商家");
        private Integer value;

        private String label;

        AssumeFreight(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

}
