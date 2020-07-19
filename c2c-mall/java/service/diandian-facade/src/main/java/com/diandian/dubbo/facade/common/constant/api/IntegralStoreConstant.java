package com.diandian.dubbo.facade.common.constant.api;

/**
 *
 * @author cjunyuan
 * @date 2019/5/10 10:53
 */
public class IntegralStoreConstant {

    public static final Integer ACCESS_TOKEN_EXPIRE = 7200;

    public static final Integer ACCESS_TOKEN_LENGTH = 64;

    public static final Integer MAX_PAGE_SIZE = 50;

    public static final int ERROR_40000_CODE = 40000;

    public static final String ERROR_40000_MESSAGE = "系统繁忙,请稍后再试";

    public static final int ERROR_40001_CODE = 40001;

    public static final String ERROR_40001_MESSAGE = "appId不能为空";

    public static final int ERROR_40002_CODE = 40002;

    public static final String ERROR_40002_MESSAGE = "appSecret不能为空";

    public static final int ERROR_40003_CODE = 40003;

    public static final String ERROR_40003_MESSAGE = "timestamp不能为空";

    public static final int ERROR_40004_CODE = 40004;

    public static final String ERROR_40004_MESSAGE = "非法的appId";

    public static final int ERROR_40005_CODE = 40005;

    public static final String ERROR_40005_MESSAGE = "appSecret不正确";

    public static final int ERROR_40006_CODE = 40006;

    public static final String ERROR_40006_MESSAGE = "parentId不能为空";

    public static final int ERROR_40007_CODE = 40007;

    public static final String ERROR_40007_MESSAGE = "accessToken已过期";

    public static final int ERROR_40008_CODE = 40008;

    public static final String ERROR_40008_MESSAGE = "非法的timestamp";

    public static final int ERROR_40009_CODE = 40009;

    public static final String ERROR_40009_MESSAGE = "请输入正确的省份编码";

    public static final int ERROR_40010_CODE = 40010;

    public static final String ERROR_40010_MESSAGE = "请输入正确的城市编码";

    public static final int ERROR_40011_CODE = 40011;

    public static final String ERROR_40011_MESSAGE = "请输入正确的区/县编码";

    public static final int ERROR_40012_CODE = 40012;

    public static final String ERROR_40012_MESSAGE = "请输入详细的收货地址";

    public static final int ERROR_40013_CODE = 40013;

    public static final String ERROR_40013_MESSAGE = "请输入收件人姓名";

    public static final int ERROR_40014_CODE = 40014;

    public static final String ERROR_40014_MESSAGE = "请输入收件人手机号码";

    public static final int ERROR_40015_CODE = 40015;

    public static final String ERROR_40015_MESSAGE = "请输入商户订单号";

    public static final int ERROR_40016_CODE = 40016;

    public static final String ERROR_40016_MESSAGE = "请输入正确的商品ID";

    public static final int ERROR_40017_CODE = 40017;

    public static final String ERROR_40017_MESSAGE = "请输入下单的商品数量";

    public static final int ERROR_40018_CODE = 40018;

    public static final String ERROR_40018_MESSAGE = "请输入下单的商品价格";

    public static final int ERROR_40019_CODE = 40019;

    public static final String ERROR_40019_MESSAGE = "请输入下单的商品信息";

    public static final int ERROR_40020_CODE = 40020;

    public static final String ERROR_40020_MESSAGE = "商品信息不存在或商品已下架";

    public static final int ERROR_40021_CODE = 40021;

    public static final String ERROR_40021_MESSAGE = "商品信息错误";

    public static final int ERROR_40022_CODE = 40022;

    public static final String ERROR_40022_MESSAGE = "无法送达输入的地址信息";

    public static final int ERROR_40023_CODE = 40023;

    public static final String ERROR_40023_MESSAGE = "%s商品库存不足";

    public static final int ERROR_40024_CODE = 40024;

    public static final String ERROR_40024_MESSAGE = "商户订单编号已存在";

    public static final int ERROR_40025_CODE = 40025;

    public static final String ERROR_40025_MESSAGE = "商品未添加运输方式";

    public static final int ERROR_40026_CODE = 40026;

    public static final String ERROR_40026_MESSAGE = "订单信息不存在";

    public static final int ERROR_40027_CODE = 40027;

    public static final String ERROR_40027_MESSAGE = "商户储备金不足";

    public static final int ERROR_40028_CODE = 40028;

    public static final String ERROR_40028_MESSAGE = "订单已过期，请重新下单";

    public static final int ERROR_40029_CODE = 40029;

    public static final String ERROR_40029_MESSAGE = "订单已支付，请勿重复提交";

    public static final int ERROR_40030_CODE = 40030;

    public static final String ERROR_40030_MESSAGE = "商品价格已变动，无法正常下单";

    public static final int ERROR_40050_CODE = 40050;

    public static final String ERROR_40050_MESSAGE = "api请求密钥错误";

    public static final int ERROR_40051_CODE = 40051;

    public static final String ERROR_40051_MESSAGE = "手机号码不能为空";

    public static final int ERROR_40052_CODE = 40052;

    public static final String ERROR_40052_MESSAGE = "软件类型信息不存在";

    public static final int ERROR_41000_CODE = 41000;

    public static final String ERROR_41000_MESSAGE = "商户信息不存在";

    public static final int ERROR_41001_CODE = 41001;

    public static final String ERROR_41001_MESSAGE = "商户未认证";

    public static final int ERROR_41002_CODE = 41002;

    public static final String ERROR_41002_MESSAGE = "商户已被禁用";

    public static final int ERROR_41003_CODE = 41003;

    public static final String ERROR_41003_MESSAGE = "商户已过期";

    public static final int ERROR_41004_CODE = 41004;

    public static final String ERROR_41004_MESSAGE = "商户未设置运费承担信息";

    public static final int ERROR_41010_CODE = 41010;

    public static final String ERROR_41010_MESSAGE = "签名错误";

    public static final int ERROR_41011_CODE = 41011;

    public static final String ERROR_41011_MESSAGE = "系统参数错误，请联系技术人员";

    public static final int ERROR_41012_CODE = 41012;

    public static final String ERROR_41012_MESSAGE = "请求IP未在商户设置的白名单中";

    public enum TransportRemark{
        BY_NUMBER(0, "%s件以内%s元，每增加%s件，增加运费%s元"),
        BY_WEIGHT(1, "首重%s公斤以内%s元，每增加%s公斤，增加运费%s元"),
        BY_VOLUME(2, "运费%s元/%s立方米，体积不足%s立方米按%s立方米计算");

        private Integer value;

        private String label;

        TransportRemark(Integer value, String label){
            this.value = value;
            this.label = label;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

    }
}
