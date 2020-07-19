package com.diandian.admin.model.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.admin.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户与角色对应关系
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:29
 */
@Getter
@Setter
@ToString
@TableName("sys_user_role")
public class SysUserRoleModel extends BaseModel {


    private static final long serialVersionUID = 5328530205085759766L;
    /**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 角色ID
	 */
	private Long roleId;

}
