package com.diandian.dubbo.facade.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wubc
 * @date 2019/2/14 23:25
 */
@Data
public class MemberInfoVO implements Serializable {

    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 类型 0 普通 ；1 注册；2 储值
     */
    private Integer type;

    /**
     * 状态 0 正常；1 禁用；2过期
     */
    private Integer state;

    /**
     * 会员昵称
     */
    private String niceName;

    /**
     * 会员编码
     */
    private String code;

    /**
     * 会员帐号
     */
    private String accountNo;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 商户ID
     */
    private Long merchantId;

    /**
     * 商户帐号
     */
    private String merchantLoginName;


    /**
     * 可用余额
     */
    private BigDecimal availBalance;

    /**
     * 冻结余额
     */
    private BigDecimal freezeBalance;

    /**
     * 可用的兑换券数量
     */
    private Integer exchangeCouponNum;

    /**
     * 累积的兑换券数量
     */
    private Integer exchangeCouponSum;

    /**
     * 可用的购物券金额
     */
    private BigDecimal shoppingCouponAmount;

    /**
     * 累积购物券金额
     */
    private BigDecimal shoppingCouponSum;

    /**
     * 累积消费次数
     */
    private Integer consumeTimesSum;

}
