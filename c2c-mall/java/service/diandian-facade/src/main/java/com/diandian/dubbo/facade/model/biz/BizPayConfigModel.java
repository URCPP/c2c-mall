package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 支付管理表
 *
 * @author zweize
 * @date 2019/03/04
 */
@Data
@TableName("biz_pay_config")
public class BizPayConfigModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 支付名称
	 */
    @TableField("pay_name")
	private String payName;
 
 	/**
	 * 支付编码
	 */
    @TableField("pay_code")
	private String payCode;
 
 	/**
	 * 支付图标
	 */
    @TableField("pay_pic")
	private String payPic;
 
 	/**
	 * 0禁用 1启动
	 */
    @TableField("state")
	private Integer state;
 
 	/**
	 * 排序
	 */
    @TableField("sort_num")
	private Integer sortNum;
 
 
 
}
