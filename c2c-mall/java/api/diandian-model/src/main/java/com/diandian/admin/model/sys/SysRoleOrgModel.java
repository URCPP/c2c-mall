package com.diandian.admin.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.admin.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色与机构对应关系
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:28
 */
@Getter
@Setter
@ToString
@TableName("sys_role_org")
public class SysRoleOrgModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 机构ID
	 */
	private Long orgId;

}
