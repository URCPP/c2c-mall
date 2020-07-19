package com.diandian.dubbo.facade.common;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.diandian.dubbo.facade.common.constant.RetEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 响应下游实体
 *
 * @author x
 * @date 2018-11-01
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RetModel implements Serializable {

    private static final long serialVersionUID = -4915346161769680701L;

    public RetModel() {
    }

    public RetModel(RetEnum retEnum) {
        this.retCode = retEnum.getCode();
        this.retMsg = retEnum.getMessage();
    }

    public RetModel(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public void buildSuccess() {
        this.setRetCode(RetEnum.RET_SUCCESS.getCode());
        this.setRetMsg(RetEnum.RET_SUCCESS.getMessage());
    }

    public void buildFail(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public void build(RetEnum retEnum) {
        this.retCode = retEnum.getCode();
        this.retMsg = retEnum.getMessage();
    }

    /**
     * 返回码
     */
    private String retCode;

    /**
     * 返回信息
     */
    private String retMsg;

    /**
     * 商户订单号
     */
    private String merchantOrderNo;

    /**
     * 商户号
     */
    private String merchantCode;

    /**
     * 交易订单号
     */
    private String platOrderNo;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 实际金额
     */
//    private BigDecimal actualAmount;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 表单数据
     */
    private Map<String, Object> formData;

    /**
     * 页面URL地址
     */
    private String urlData;

    /**
     * 扩展数据
     */
    private Map<String, Object> extData;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 签名
     */
    private String sign;


    public String toJson() {
        return JSON.toJSONString(this);
    }
}
