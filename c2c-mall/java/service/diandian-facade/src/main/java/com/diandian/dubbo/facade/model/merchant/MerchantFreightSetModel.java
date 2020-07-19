package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 商户运费设置表
 *
 * @author wbc
 * @date 2019/02/20
 */
@Data
@TableName("merchant_freight_set")
public class MerchantFreightSetModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 商户ID
	 */
    @TableField("merchant_id")
	private Long merchantId;
 
 	/**
	 * 运费模板
	 */
    @TableField("freight_template_id")
	private Long freightTemplateId;
 
 	/**
	 * 运费模板
	 */
    @TableField("freight_template")
	private String freightTemplate;
 
 	/**
	 * 运费编号
	 */
    @TableField("freight_code")
	private String freightCode;
 
 	/**
	 * 状态(0 正常 ，1 禁用)
	 */
    @TableField("freight_state_flag")
	private Integer freightStateFlag;
 
 	/**
	 * 是否运费承担(0 会员, 1 商家)
	 */
    @TableField("assume_flag")
	private Integer assumeFlag;

	/**
	 * 商户积分商城商品展示样式（1，2，3）
	 */
	@TableField("product_show_style")
	private Integer productShowStyle;

 
 
 	/**
	 * 
	 */
    @TableField("remark")
	private String remark;
 
}
