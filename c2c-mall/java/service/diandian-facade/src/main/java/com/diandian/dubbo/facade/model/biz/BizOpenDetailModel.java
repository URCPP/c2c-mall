package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 机构开通明细表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Data
@TableName("biz_open_detail")
public class BizOpenDetailModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 交易编号
	 */
    @TableField("trade_no")
	private String tradeNo;
 
 	/**
	 * 交易类型（0收入1支出）
	 */
    @TableField("trade_type")
	private Integer tradeType;
 
 	/**
	 * 业务类型（0,开通，1推荐奖励，2审核失败退回，3开通机构消耗）
	 */
    @TableField("bus_type")
	private Integer busType;
 
 	/**
	 * 开通类型 （0：机构，1：软件）
	 */
    @TableField("open_type")
	private Integer openType;
 
 	/**
	 * 策略ID(机构类型ID  或 软件类型ID)
	 */
    @TableField("type_id")
	private Long typeId;
 
 	/**
	 * 交易数量
	 */
    @TableField("trade_num")
	private Integer tradeNum;
 
 	/**
	 * 交易前数量
	 */
    @TableField("trade_start")
	private Integer tradeStart;
 
 	/**
	 * 交易后数量
	 */
    @TableField("trade_end")
	private Integer tradeEnd;
 
 	/**
	 * 机构ID
	 */
    @TableField("org_id")
	private Long orgId;
 
 	/**
	 * 开通机构对象ID
	 */
    @TableField("from_org_id")
	private Long fromOrgId;
 
 	/**
	 * 
	 */
    @TableField("remark")
	private String remark;
 
 
 
}
