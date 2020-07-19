package com.diandian.dubbo.facade.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 机构管理
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:28
 */
@Getter
@Setter
@ToString
@TableName("sys_org_type")
public class SysOrgTypeModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	@TableField("type_code")
	private String typeCode;

	@TableField("type_name")
	private String typeName;

	@TableField("type_explain")
	private String typeExplain;

	@TableField("level")
	private Integer level;

	@TableField("opening_cost")
	private BigDecimal openingCost;

	@TableField("personal_proportion")
	private BigDecimal personalProportion;

	@TableField("personal_direct_proportion")
	private BigDecimal personalDirectProportion;

	@TableField("team_proportion")
	private BigDecimal teamProportion;

	@TableField("remark")
	private String remark;

	@TableField("del_flag")
	private Integer delFlag;

	/**
	 * 菜单ID_LIST
	 */
	@TableField(exist = false)
	private List<Long> menuIdList;

}
