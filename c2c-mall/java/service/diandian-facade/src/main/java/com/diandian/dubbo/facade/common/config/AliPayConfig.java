package com.diandian.dubbo.facade.common.config;

public class AliPayConfig {

    /**
     * 支付宝服务商 账号 为
     */
    public static String appid="2019030563482078";

    public static final String gatewayUrl="https://openapi.alipay.com/gateway.do";

    //应用对应的私钥
    public static String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC+qJYW0l1C3zOZ" +
            "ikIqdWwpv8fc/jx67Ang0TCGo4pqApTswHUj8ObJusvxGmU/3kPZcJo+d7VkBalA" +
            "jJgXUHhhGQHGqaxwP/H9Lz7B/AzvxHhkCeQe5nOUeMMYHhDpZY0PvGTJWhZxt2jn" +
            "CocWET/SVHcUUihXFegm7On8OeuG/709nBuxLhheG7FPKVYF4LwWhEO/SIeg+e+k" +
            "cX5qv37/paPI5e8iV6gC+NSfEHIeH6oVWC1dId71qJaboPhelJP3XMXW6S2yee5p" +
            "kbMu1PbcPt4c5gdEStUZHmRBXEtQyeVISrk8Bu0EOyaO1QyVOYYysgEV0oLR9QzP" +
            "rdjicQRZAgMBAAECggEAM4BIoruKc7DlOFlvjBaR9CkY7OpEHhQ6it8XE5eDoSU3" +
            "Y0iNm/rL7LwnsOYnaXw5qbfQAoXFmtIoqQYRY2LaUag2oZpduSiczn5/nljeibnx" +
            "p1xAodQwbm+8i3w7p0BGPXTvaLCMFYWssZRXby+TpNYqIHQc74E8Z59hiT+ADsP4" +
            "2D8NSAqaWVY2RfT5JXC4hgLaCP+eikWDfLbOqf4CUrggmT9fM67S0JoKaasbDcAC" +
            "EbCK6hrbBPm/yGDYo4yf3haJ057Jva7Wv4FetwuEZYQv2/JG8VqxJhMD56fUlvzJ" +
            "xlFATsRM1u4xt4qKvqzxcQS6qf4llGMSHOrf2x2Z/QKBgQDdNf6/zw4uKE9kXNhr" +
            "JY/Piiz8Nb5cvjT6jxzbEAxUGV4bhbpirU/iFdaSVoSok5kKsqQiD2agSAS8vpde" +
            "6md1i4tzegv/faLyujhe/YzJYffuhcbRALUhIfi9rqtJFZmSQ7yV7wt1mxNeSic5" +
            "PAKlG7Z96nmc4VQawmUslsgEuwKBgQDcpIuJGdgJ94zwWfe8b8FFTFT3DOvbaSN6" +
            "D9EY0o5RG+TGUqTEa3T5XyyKVNg+5I7eLchEIBT481vpc0p0u2gOHQ7h+I4H+skf" +
            "mcWM7kgVoZojV/vmdcTlVory9bkWuY50B8PhNugCA7KgM37sy+D3Pot5eFqYp1E+" +
            "ovfTaYeT+wKBgAy6faUVyfAX+7F/sihAKg08GyiMAx8dBrlgIL3uJ3dzWjnlMPta" +
            "k50juowDFHEKxSSbcNxQ3IveeP9PTDB29msKE4UagbQ6y3SCCJpWppIz4+tCoVUH" +
            "YGS+qqxnb42uA38qDKDn91XWDYBAbg9aB6DufkqygjrnsEo141J0fp/fAoGATWS6" +
            "PSD/kp9upHAqYcKhfHlF5FJ3+MJsW0hpA9f3a6PpH1ely/AVW9NygCM6pvTs2Coe" +
            "8SZ/tMNIHh7+ouuJmEnBCqpwx4PEQH1jugdq6YuB0ZvvE+uDYR52JrribYIw4apH" +
            "nxvvTaWWZnMptk3dX1xk9/qrbJhj+yCW5WVC1TkCgYAtZx/aPY/XvllXAXk/SZZI" +
            "MbGkO6MPxTHRHFR4JTYxfUDH4+RcCuZQcfkkULaZ6YhDaafOxQyjWTH+cCv23jjU" +
            "020ThRqBFAA/IEN3nLuSqL0Z9QTw2LDDjMcJMYsswhmr7psPc4gq7QINaV0lYAtt" +
            "8No8Xtz9qlZGV4d9pSVjKw==";

    // 支付宝的公钥
    public static String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjLPx6WD/hOwQJ8pc4kM9/NwVG0NwC8rGN2J3pb+K7AbuO8veDaVqzILhGKcSAMbBmq9f1RpRuyrbnyW6+kwiBU8FDmhHoryj4jhbvLBis+ssel3O7cnafgc1raFYN0ukGuGHzabrNts2aK/cuIkvDlVwZ1mZQyAdEfuCFx1OIruOdoulTGnFoMjlWvfZrbfRojlUYIBw+A2whsq/AhyDEuqUeKKtXODbppBKDWqCrJDbTf0sdayafF4DpfuKkwVNSwtK60xoSwVDuNz+fMJP5Kq9kPYrKODvp8FeozitoFBHLhPzR6LrnFs4IXtW1WJIGmN45E0RUIZ7ZMWoDAcWgwIDAQAB";

    public static String format="json";

    public static String charset="UTF-8";

    //商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
    public static String signType="RSA2";

    //商城回调地址
    public static String mallUrl= "http://sc.dd3c.cn";
    //手机商城回调地址
    public static String phoneMallUrl= "http://sc.dd3c.cn";

    public static String mallPort = "/dwm";

    //积分商城回调地址
    public static String mchUrl = "http://mmpm.dd3c.cn";

    public static String mchPost = "/mm";

    //支付成功，回跳地址
    public static String returnUrl = "/#/paySuccess";

    //订单支付回调地址
    public static String orderNotifyUrl = "/pay/callBack/order";

    //商户（充值，续费）支付回调地址
    public static String merchantNotifyUrl = "/pay/callBack/merchant";

    public static String integralMallNotifyUrl = "/pay/callBack/integralMall";

}
