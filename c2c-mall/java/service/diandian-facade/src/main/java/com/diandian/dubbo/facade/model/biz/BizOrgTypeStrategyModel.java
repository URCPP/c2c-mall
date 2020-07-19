package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 机构类型策略
 *
 * @author zweize
 * @date 2019/02/19
 */
@Data
@TableName("biz_org_type_strategy")
public class BizOrgTypeStrategyModel extends BaseModel {

	private static final long serialVersionUID = 1L;

 	/**
	 * 机构类型ID
	 */
    @TableField("org_type_id")
	private Long orgTypeId;

	/**
	 * 机构类型名称
	 */
	@TableField("org_type_name")
	private String orgTypeName;

 	/**
	 * 策略类型（0：开通赠送，1：推荐奖励）
	 */
    @TableField("strategy_type")
	private Integer strategyType;
 
 	/**
	 * 策略类型名
	 */
    @TableField("strategy_type_name")
	private String strategyTypeName;
 
 	/**
	 * 推荐类型（0：策略类型不是推荐奖励，1：机构，2：软件）
	 */
    @TableField("recommend_type")
	private Integer recommendType;
 
 	/**
	 * 推荐类型名
	 */
    @TableField("recommend_type_name")
	private String recommendTypeName;

	/**
	 * 推荐类型为1机构特有字段-机构类型ID
	 */
	@TableField("recommend_org_type_id")
	private Long recommendOrgTypeId;

	/**
	 * 推荐类型为2软件特有字段-软件类型ID
	 */
	@TableField("recommend_software_type_id")
	private Long recommendSoftwareTypeId;

 	/**
	 * 奖励类型（0：奖励机构，1：奖励软件，2：奖励现金，3：奖励首次年费，4：奖励续费年费）
	 */
    @TableField("reward_type")
	private Integer rewardType;
 
 	/**
	 * 奖励类型名
	 */
    @TableField("reward_type_name")
	private String rewardTypeName;
 
 	/**
	 * 奖励类型为1机构特有字段-机构类型ID
	 */
    @TableField("reward_org_type_id")
	private Long rewardOrgTypeId;
 
 	/**
	 * 奖励类型为2软件特有字段-软件类型ID
	 */
    @TableField("reward_software_type_id")
	private Long rewardSoftwareTypeId;
 
 	/**
	 * 奖励值（为年费奖励时次值就比率）
	 */
    @TableField("reward_value")
	private BigDecimal rewardValue;
 
 	/**
	 * 状态（0：启用，1：禁用）
	 */
    @TableField("state")
	private Integer state;
 
 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;
 
}
