package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 平台总配置表
 *
 * @author jbh
 * @date 2019/03/14
 */
@Data
@TableName("biz_config")
public class BizConfigModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 客服联系方式
	 */
    @TableField("server_contact")
	private String serverContact;
 
 	/**
	 * 帮助中心是否最新（0 是，1否）
	 */
    @TableField("help_flag")
	private Integer helpFlag;
 
 	/**
	 * 官方营业执照图片地址
	 */
    @TableField("official_business_license_pic")
	private String officialBusinessLicensePic;
 
 	/**
	 * APP下载URL
	 */
    @TableField("app_url")
	private String appUrl;
 
 	/**
	 * 兑换商城URL
	 */
    @TableField("exchange_mall_url")
	private String exchangeMallUrl;
 
 	/**
	 * 保证金
	 */
    @TableField("margin_amount")
	private BigDecimal marginAmount;
 
 	/**
	 * 自定义商城开通费用
	 */
    @TableField("mall_open_fee")
	private BigDecimal mallOpenFee;
 
 	/**
	 * 官方电话
	 */
    @TableField("official_tel")
	private String officialTel;

	/**
	 * 税点
	 */
	@TableField("tax_point")
	private BigDecimal taxPoint;

	/**
	 * api密钥
	 */
	@TableField("api_secret")
	private String apiSecret;

 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;
 
}
