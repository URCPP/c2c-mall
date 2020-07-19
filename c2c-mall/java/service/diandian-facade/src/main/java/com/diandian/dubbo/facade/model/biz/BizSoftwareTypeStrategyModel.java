package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 软件类型策略
 *
 * @author zweize
 * @date 2019/02/19
 */
@Data
@TableName("biz_software_type_strategy")
public class BizSoftwareTypeStrategyModel extends BaseModel {

	private static final long serialVersionUID = 1L;

 	/**
	 * 软件类型ID
	 */
    @TableField("software_type_id")
	private Long softwareTypeId;
 
 	/**
	 * 策略类型（0：开通赠送，1：推荐奖励）
	 */
    @TableField("strategy_type")
	private Integer strategyType;

	/**
	 * 推荐软件类型ID
	 */
	@TableField("recommend_software_type_id")
	private Long recommendSoftwareTypeId;
 
 	/**
	 * 奖励类型（0：奖励机构 ，1：奖励软件，2：奖励现金，3：奖励首次年费，4：奖励续费年费）
	 */
    @TableField("reward_type")
	private Integer rewardType;
 
 	/**
	 * 奖励类型为0软件特有字段-软件类型ID
	 */
    @TableField("reward_software_type_id")
	private Long rewardSoftwareTypeId;
 
 	/**
	 * 奖励值（为年费奖励时次值就比率）
	 */
    @TableField("reward_value")
	private BigDecimal rewardValue;

	/**
	 * 状态
	 */
	@TableField("state")
	private Integer state;

 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;
 
}
