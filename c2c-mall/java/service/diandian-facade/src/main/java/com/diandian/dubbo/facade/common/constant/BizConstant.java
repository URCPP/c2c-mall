package com.diandian.dubbo.facade.common.constant;

/**
 * @author cjunyuan
 * @date 2018/12/10 18:00
 */
public class BizConstant {

    public static final Integer STATE_NORMAL = 0;

    public static final Integer STATE_DISNORMAL = 1;

    //支付设置过期时间(分钟)
    public static final Long TIMEOUT_EXPRESS = 28l;

    /**
     * 商品上架状态
     */
    public static final Integer PRODUCT_ONSALE = 1;

    public static final String PAY_TOKEN = "PAY_TOKEN_%S";

    /**
     * 充值单号前缀
     */
    public static final String NO_TYPE_RECHARGE = "RC%s";

    /**
     * 兑换单号前缀
     */
    public static final String NO_TYPE_EXCHANGE = "EX%s";

    /**
     * 商城订单单号前缀
     */
    public static final String NO_TYPE_SHOP_ORDER = "SP%s";

    /**
     * 商城公共订单单号前缀
     */
    public static final String PUBLIC_SHOP_ORDER = "PU%s";

    /**
     * 申请订单单号前缀
     */
    public static final String NO_TYPE_APPLY_ORDER = "AP%s";

    /**
     * 奖金流水编号前缀
     */
    public static final String NO_TYPE_ORG_REWARD = "RW%s";

    /**
     * 交易明细编号
     */
    public static final String NO_TRADE = "TN%s";

    /**
     * 机构编号前缀
     */
    public static final String NO_TYPE_ORG_PREFIX = "800";

    /**
     * 机构编号前缀
     */
    public static final String NO_TYPE_MERCHANT_PREFIX = "900";

    /**
     * 机构账户编号前缀
     */
    public static final String NO_TYPE_ORG_ACCOUNT = "OA%s";

    /**
     * 机构提现编号前缀
     */
    public static final String NO_WITHDRAWAL_APPLY = "WA%s";

    /**
     * 获取商户订单支付编号前缀
     */
    public static final String NO_MERCHANT_PAY = "MP%s";
    /**
     * 获取商户订单支付编号前缀
     */
    public static final String NO_MERCHANT_OFFLINE_PAY = "MOP%s";
    /**
     * 获取商户开放平台appId前缀
     */
    public static final String NO_MERCHANT_APP_ID = "mch%s";
    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    public enum ApplyType {
        APPLY(0, "申请"),
        OPEN(1, "开通");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        ApplyType(Integer value, String label) {
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

    public enum AuditState {
        AUDIT_WAIT(0, "待审核"),
        AUDIT_PASSED(1, "审核通过"),
        AUDIT_NOT_PASSED(2, "审核不通过"),
        CLOSE_APPLY(3, "关闭申请");
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
     * 策略类型
     */
    public enum StrategyType {
        OPENING_GIFTS(0, "开通赠送"),
        REFERRAL_BONUSES(1, "推荐奖励"),
        OPENING_BONUSES(2, "开通奖励");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        StrategyType(Integer value, String label) {
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
     * 推荐类型
     */
    public enum RecommendType {
        OPENING_GIFTS(2, "无"),
        ORG(0, "机构"),
        SOFTWARE(1, "软件");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String label;

        RecommendType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer value() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public static String getLabel(Integer value){
            for(RecommendType recommendType : RecommendType.values()){
                if(value.equals(recommendType.value())){
                    return recommendType.getLabel();
                }
            }
            return  null;
        }
    }

    /**
     * 奖励类型
     */
    public enum RewardType {
        ORG(0, "奖励机构"),
        SOFTWARE(1, "奖励软件"),
        CASH(2, "奖励现金"),
        FIRST_TIME(3, "奖励首次年费"),
        RENEWAL(4, "奖励续费年费");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String label;

        RewardType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer value() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public static String getLabel(Integer value){
            for(RewardType rewardType : RewardType.values()){
                if(value.equals(rewardType.value())){
                    return rewardType.getLabel();
                }
            }
            return  null;
        }
    }

    /**
     * 交易类型
     */
    public enum TradeType {
        INCOME(0, "收入"),
        EXPENDITURE(1, "支出");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        TradeType(Integer value, String label) {
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
     * 销售业绩业务类型
     */
    public enum CommissionBusType {
        PERSON(0, "个人销售业绩"),
        TEAM(1, "团队销售业绩"),
        REFUND(2, "订单退款"),
        TRANSFER_BALANCE(3,"转余额");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        CommissionBusType(Integer value, String label) {
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
     * 账号明细业务类型
     */
    public enum AccountBusType {
        RECOMMEND(0, "推荐奖励"),
        RENEW(1, "续费奖励"),
        SALES(2, "待结算销售佣金转入"),
        BONUS(3, "待结算奖金转入"),
        WITHDRAW_ROLLBACK(4, "提现失败退还"),
        WITHDRAW(5, "提现支出"),
        UNDO_APPLY(6, "撤消申请奖励");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        AccountBusType(Integer value, String label) {
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
     * 账号明细业务类型
     */
    public enum BonusBusType {
        RECOMMEND(0, "推荐奖励"),
        RENEW(1, "续费奖励"),
        ISSUE(2, "奖金发放"),
        UNDO_APPLY(3, "撤消申请奖励");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        BonusBusType(Integer value, String label) {
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
     * 上传图片类型
     */
    public enum PicType {
        OPEN(0, "开通"),
        WITHDRAW(1, "提现");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        PicType(Integer value, String label) {
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
     * 奖金发放
     */
    public enum BonusType {
        SCENE(0, "现场发放"),
        BALANCE(1, "转余额");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        BonusType(Integer value, String label) {
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
     * 申请对象类型
     *
     * @author cjunyuan
     * @date 2019/2/25 10:13
     */
    public enum ObjectType {
        ORG(0, "机构"),
        SOFTWARE(1, "软件");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String label;

        ObjectType(Integer value, String label) {
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

    public enum CatalogType {
        CATALOG(0, "目录"),
        PIC(1, "图片"),
        GRAPHIC(2, "图文");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        CatalogType(Integer value, String label) {
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
     * 商户收货地址类型
     */
    public enum MerRecipientsType {
        COMPANY(0, "公司"),
        HOME(1, "家");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        MerRecipientsType(Integer value, String label) {
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
     * 商户收货地址默认
     */
    public enum MerRecipientsDefault {
        UNDEFAULT(0, "非默认"),
        DEFAULT(1, "默认");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        MerRecipientsDefault(Integer value, String label) {
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
     * 商户收货地址状态
     */
    public enum MerRecipientsState {
        NORMAL(0, "正常"),
        DISABLED(1, "禁用");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        MerRecipientsState(Integer value, String label) {
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
     * 商户预售商品状态
     */
    public enum MerProductState {
        OFF_AWAY(0, "下架"),
        PUT_AWAY(1, "上架");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        MerProductState(Integer value, String label) {
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
     * 平台商品状态
     */
    public enum ProductState {
        OFF_AWAY(99, "下架"),
        PRESELL(2, "预售"),
        STAY_ON(0, "未上架"),
        PUT_AWAY(1, "上架");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        ProductState(Integer value, String label) {
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
        NOT_PAY(0, "待付款"),
        PAYMENT(1, "待发货"),
        SEND(2, "已发货"),
        RECEIPT(3, "已完成"),
        //        REFUND_APPLY(4, "申请退款"),
//        RETURN_DOING(5, "退货中"),
//        RETURN_GOODS(6, "已退货"),
//        REFUSED_RETURN(7, "拒绝退货"),
        CLOSE_ORDER(98, "关闭订单"),
        ERROR_ORDER(99, "异常订单");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        OrderState(Integer value, String lable) {
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
     * 订单分润标识
     */
    public enum OrderShareFlag {
        NOT_SHARE(0, "未分润"),
        SHARE(1, "已分润"),
        RETURN_SHARE(2, "分润返还");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        OrderShareFlag(Integer value, String label) {
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
     * 订单类型
     */
    public enum OrderType {
        AMOUNT_ORDER(0, "金额订单"),
        INTERGRAL_ORDER(1, "积分订单"),
        THIRD_ORDER(2, "第三方订单");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        OrderType(Integer value, String lable) {
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
     * 订单售后标识
     */
    public enum OrderAfterSaleFlag {
        NORMAL(0, "正常"),
        AFTER_SALE(1, "售后状态");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        OrderAfterSaleFlag(Integer value, String lable) {
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
     * 订单售后状态
     */
    public enum OrderAfterSaleState {
        APPLY(0, "正申请售后"),
        DOING(1, "售后中"),
        COMPLETE(2,"售后完成"),
        REFUSE(3,"拒绝售后"),
        UNDO(4,"撤销售后");
        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        OrderAfterSaleState(Integer value, String label) {
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
     * 支付状态
     */
    public enum OrderPayState {
        PAY_FAIL(0, "支付失败"),
        PAY_SUCCESS(1, "支付成功"),
        REFUND_SUCCESS(2, "退款成功");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String lable;

        OrderPayState(Integer value, String label) {
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
     * 开通详情业务类型
     */
    public enum OpenBusType {
        OPEN_REWARD(0, "开通奖励"),
        RECOMMEND_REWARD(1, "推荐奖励"),
        AUDIT_FAILED(2, "退回"),
        OPEN_SUCCESS(3, "消耗"),
        ADMIN_EDIT(4, "管理员修改"),
        UNDO_APPLY(5, "撤消申请奖励");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String label;

        OpenBusType(Integer value, String label) {
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
     * 开通详情业务类型
     */
    public enum CompareType {
        LT("lt", "小于"),
        LE("le", "小于且等于"),
        GT("gt", "大于"),
        GE("ge", "大于且等于"),
        EQ("eq", "等于");

        /**
         * 值
         */
        private String value;
        /**
         * 描述
         */
        private String label;

        CompareType(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String value() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }

    /**
     * 开通详情业务类型
     */
    public enum StatisticalUnit {
        DAY(0, "%Y-%m-%d"),
        MONTH(1, "%Y-%m");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String label;

        StatisticalUnit(Integer value, String label) {
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
     * 运输单状态（polling:监控中，shutdown:结束，abort:中止，updateall：重新推送）
     */
    public enum TransportStatus {
        POLLING("polling", "监控中"),
        SHUTDOWN("shutdown", "结束"),
        ABORT("abort", "中止"),
        UPDATEALL("updateall", "重新推送");

        /**
         * 值
         */
        private String value;
        /**
         * 描述
         */
        private String label;

        TransportStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String value() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }

    /**
     * 商户采购券记录类型
     */
    public enum PurchaseVoucherLogType {
        OPENING(0, "开通购买"),
        BUY_PRODUCT(1, "购买商品");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String label;

        PurchaseVoucherLogType(Integer value, String label) {
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
     * 商户采购券记录类型
     */
    public enum WeixinMaterialType {
        IMAGE(1, "图片"),
        VIDEO(2, "视频"),
        VOICE(3, "音频"),
        NEWS(4, "图文");

        /**
         * 值
         */
        private Integer value;
        /**
         * 描述
         */
        private String label;

        WeixinMaterialType(Integer value, String label) {
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
