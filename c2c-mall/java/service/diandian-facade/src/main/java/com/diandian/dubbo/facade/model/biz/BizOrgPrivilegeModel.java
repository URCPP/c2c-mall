package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 机构奖励信息
 * @author cjunyuan
 * @date 2019/2/20 18:36
 */
@Data
@TableName("biz_org_privilege")
public class BizOrgPrivilegeModel extends BaseModel {

	private static final long serialVersionUID = 672973587622798635L;

	/**
	 * 机构ID
	 */
    @TableField("org_id")
	private Long orgId;

	/**
	 * 奖励类型名
	 */
	@TableField("reward_type_id")
	private Integer rewardTypeId;

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

}
