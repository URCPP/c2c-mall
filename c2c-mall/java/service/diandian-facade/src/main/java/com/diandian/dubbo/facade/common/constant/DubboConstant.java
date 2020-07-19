package com.diandian.dubbo.facade.common.constant;

import java.util.regex.Pattern;

/**
 * @author cjunyuan
 * @date 2019/7/9 13:51
 */
public class DubboConstant{
/*
    *//**
     * 推荐树根节点
     *//*
    public final static String RECOMMEND_TREE_ROOT = "0";

    *//**
     * 身份编号前缀
     *//*
    public final static String IDENTITY_CODE_PREFIX = "900";

    *//**
     * 会员编号前缀
     *//*
    public final static String MEMBER_CODE_PREFIX = "800";

    *//**
     * 邀请码前缀
     *//*
    public final static String INVITATION_CODE_PREFIX = "700";*/

    /**
     * 会员登录有效时间，单位：秒
     */
    public final static Integer MEMBER_LOGIN_EXPIRE_TIME = 604800;

  /*  *//**
     * 阿里云oss默认前缀
     *//*
    public final static String OSS_PREFIX = "diandian-platform";

    *//**
     * 阿里云oss图片默认后缀
     *//*
    public final static String OSS_IMAGE_FORMAT = ".jpg";

    *//**
     * 会员账户记录编号前缀
     *//*
    public final static String MEMBER_ACCOUNT_RECORD_NO_PREFIX = "MA";

    public final static Long ORDER_EXPIRE_TIMES = 30L;

    public final static Pattern WX_TMP_MSG_REGEX = Pattern.compile("\\{\\{(.*?).DATA\\}\\}");

    public final static Pattern TMP_MSG_INDEX_REGEX = Pattern.compile("\\{(\\d*?)\\}");

    public final static String RECOMMEND_INCOME_MSG_TMP_NAME = "recommend_income";*/

    /*public enum ResourceType {
        IMAGE("image", "图片"),
        VIDEO("video", "视频"),
        AUDIO("audio", "音频"),
        DOCUMENT("document", "文档");

        private String value;

        private String label;

        ResourceType(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }*/

   /* public enum IdentityType{
        AGENCY_SYSTEM(1, "代理体系"),
        MEMBER_SYSTEM(2, "会员体系");

        private Integer value;

        private String label;

        IdentityType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }*/

    /**
     * 身份注册类型（1-主动升级，2-自动升级，3-管理员添加/修改）
     * @author cjunyuan
     * @date 2019/7/19 10:13
     */
  /*  public enum IdentityRegisterType{
        PROACTIVE_UPGRADE(1, "主动升级"),
        AUTO_UPGRADE(2, "自动升级"),
        ADMINISTRATOR_EDIT(3, "管理员添加/修改"),
        OPEN_GIFT(4, "开通赠送"),
        RECOMMEND_OPEN(5, "推荐人权益开通");

        private Integer value;

        private String label;

        IdentityRegisterType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }*/

    /**
     * 会员账户交易对象（1-余额，2-DB）
     * @author cjunyuan
     * @date 2019/7/25 10:48
     */
   /* public enum MemberAccountTradeObject {

        BALANCE(1, "余额"),
        DB(2, "DB");

        private Integer value;

        private String label;

        MemberAccountTradeObject(Integer value, String label){
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

    }*/

    /**
     * 会员账户业务类型（1-直接推荐收益，2-间接推荐收益，3-同级业绩收益，4-DB转余额）
     * @author cjunyuan
     * @date 2019/7/25 10:48
     */
    /*public enum MemberAccountBusinessType {

        DIRECT_RECOMMEND(1, "直接推荐收益"),
        INDIRECT_RECOMMEND(2, "间接推荐收益"),
        SAME_LEVEL_COMMISSION(3, "同级业绩收益"),
        DB_TO_BALANCE(4, "DB转余额"),
        WITHDRAW(5, "提现"),
        WITHDRAW_REJECT(8, "提现退回"),
        ORDER(6, "商品订单收益"),
        LEVEL_DIFF_DIRECT_RECOMMEND(7, "等级差直接推荐收益"),
        PRODUCT_EARNINGS(9, "点点商城商品分销利润"),
        SHARE_EARNINGS(10, "点点商城商品加价分享利润");

        private Integer value;

        private String label;

        MemberAccountBusinessType(Integer value, String label){
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

    }*/

    /**
     * 交易类型（1-收入，2-支出）
     * @author cjunyuan
     * @date 2019/7/25 10:48
     */
   /* public enum TradeType{

        INCOME(1, "收入"),
        EXPENDITURE(2, "支出");

        private Integer value;

        private String label;

        TradeType(Integer value, String label){
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

    }*/

    /**
     * 订单类型（1-商品订单，2-开市礼包订单）
     * @author cjunyuan
     * @date 2019/7/25 16:41
     */
    /*public enum OrderType{

        PRODUCT_ORDER(1, "商品订单"),
        GIFT_PACKAGE_ORDER(2, "开市礼包订单");

        private Integer value;

        private String label;

        OrderType(Integer value, String label){
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

    }*/

    /**
     * 支付类型（MOBILE_ALIPAY-支付宝手机端支付，H5_WXPAY-微信H5支付，MP_WXPAY-微信公众号支付）
     * @author cjunyuan
     * @date 2019/7/25 16:41
     */
  /*  public enum PayType{

        H5_ALIPAY("H5_ALIPAY", "支付宝手机端支付"),
        H5_WXPAY("H5_WXPAY", "微信H5支付"),
        MP_WXPAY("MP_WXPAY", "微信公众号支付");

        private String value;

        private String label;

        PayType(String value, String label){
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

    }*/

    /**
     * 支付状态
     * @author cjunyuan
     * @date 2019/7/25 17:15
     */
    /*public enum OrderPayState {
        PAY_FAIL(0, "支付失败"),
        PAY_SUCCESS(1, "支付成功"),
        REFUND_SUCCESS(2, "退款成功");

        *//**
         * 值
         *//*
        private Integer value;
        *//**
         * 描述
         *//*
        private String label;

        OrderPayState(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }*/

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
        private String label;

        OrderState(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }

    /**
     * 售后状态
     * @author cjunyuan
     * @date 2019/7/29 10:00
     */
   /* public enum AfterSalesState{
        NOT_AFTER_SALE(0, "未售后"),
        AFTER_SALES_PROCESSING(1, "售后处理中"),
        COMPLETE_AFTER_SALES(2, "售后处理完成"),
        CANCEL_AFTER_SALES(3, "撤消售后处理");

        *//**
         * 值
         *//*
        private Integer value;
        *//**
         * 描述
         *//*
        private String label;

        AfterSalesState(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }*/

    /**
     * 支付方式
     */
   /* public enum PayMethod{

        ALIPAY(1, "支付宝"),
        WXPAY(2, "微信");

        *//**
         * 值
         *//*
        private Integer value;
        *//**
         * 描述
         *//*
        private String label;

        PayMethod(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }*/

/*    public enum BenefitFromType{

        MEMBER(1, "会员"),
        PRODUCT_ORDER(2, "订单");

        *//**
         * 值
         *//*
        private Integer value;
        *//**
         * 描述
         *//*
        private String label;

        BenefitFromType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }*/

    /**
     * 产品状态
     */
    public enum ProductState {

        SALE_OUT(99, "下架"),
        SALE_WAIT(0, "待上架"),
        SALE_NARMAL(1, "上架"),
        SALE_PRE(2, "预售");

        private Integer value;

        private String label;

        ProductState(Integer value, String label) {
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

   /* public enum FeeType {

        FIXED(1, "固定金额"),
        SCALE(2, "比例");

        private Integer value;

        private String label;

        FeeType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }*/

    /**
     * 会员特权类型
     * @author cjunyuan
     * @date 2019/7/30 14:25
     */
   /* public enum PrivilegeType{

        IDENTITY(1, "身份"),
        RECOMMEND_PRODUCT(2, "产品");

        private Integer value;

        private String label;

        PrivilegeType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }*/

    /**
     * 会员权益业务类型
     * @author cjunyuan
     * @date 2019/7/30 14:25
     */
/*    public enum MemberPrivilegeBusinessType{

        OPEN_GIFT(1, "开通赠送"),
        CONSUMPTION(2, "消耗"),
        ADMIN_EDIT(3, "管理员修改");

        private Integer value;

        private String label;

        MemberPrivilegeBusinessType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }*/

    /**
     * 通知消息模板类型
     * @author cjunyuan
     * @date 2019/8/2 15:34
     */
  /*  public enum NotifyMessageTemplateType{

        WX_TEMPLATE_MSG(1, "微信模板消息"),
        SYSTEM_MSG(2, "系统消息");

        private Integer value;

        private String label;

        NotifyMessageTemplateType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }*/

    /*public enum WithdrawAuditState{
        WAIT(0, "待审核"),
        SUCCESS(1, "审核通过"),
        REJECT(2, "审核不通过");

        private Integer value;

        private String label;

        WithdrawAuditState(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }*/

/*    public enum InsteadPayTradeState{
        WAIT(0, "待请求"),
        PROCESSING(1, "处理中"),
        SUCCESS(2, "交易成功"),
        FAILURE(3, "交易失败");

        private Integer value;

        private String label;

        InsteadPayTradeState(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }*/

    /**
     * 会员股权申请状态
     * @author cjunyuan
     * @date 2019/8/22 14:50
     */
   /* public enum MemberShareApplyState{
        APPLYING(0, "申请中"),
        ACCEPTED(1, "已接受"),
        REJECTED(2, "已拒绝"),
        SIGNED(3, "已签约");

        private Integer value;

        private String label;

        MemberShareApplyState(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }*/
}
