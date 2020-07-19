package com.diandian.admin.model.sys;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.admin.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 系统用户
 * @author x
 * @date 2018/09/12 17:50:29
 */
@Getter
@Setter
@ToString
@TableName("sys_user")
public class SysUserModel extends BaseModel {

	private static final long serialVersionUID = 6920349194449193074L;
	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 用户类型(0-超级管理员,1-普通管理员,3-商户)
	 */
	private Integer type;

	/**
	 * 盐
	 */
	private String salt;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 状态  0：正常   1：禁用
	 */
	private Integer state;

	/**
	 * 是否已删除 0：未删除 1：已删除
	 */
	@TableField("del_flag")
	private Integer delFlag;

	/**
	 * 机构ID
	 */
	@TableField(value = "org_id", strategy = FieldStrategy.IGNORED)
	private Long orgId;

	/**
	 * 机构类型ID
	 */
	@TableField("org_type_id")
	private Long orgTypeId;

	/**
	 * 创建人ID
	 */
	private Long createBy;

	/**
	 * 更新人ID
	 */
	private Long updateBy;

	/**
	 * 机构名称
	 */
	@TableField(exist = false)
	private String orgName;

	/**
	 * 机构名称
	 */
	@TableField(exist = false)
	private String orgTypeName;

	/**
	 * 机构类型等级
	 */
	@TableField(exist = false)
	private Integer level;

	/**
	 * 认证状态  0未认证 ； 1认证中； 2 认证成功；3认证失败
	 */
	@TableField(exist = false)
	private Integer approveFlag;

	/**
	 * 机构树
	 */
	@TableField(exist = false)
	private String treeStr;

	/**
	 * 角色列表
	 */
	@TableField(exist = false)
	private List<Long> roleIdList;


	@TableField(exist = false)
	private List<SysMenuModel> menuList;


}
