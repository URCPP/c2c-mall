package com.diandian.dubbo.facade.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
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
@TableName("sys_org")
public class SysOrgModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 上级机构ID，一级机构为0
	 */
	@TableField("parent_id")
	private Long parentId;

	/**
	 * 机构名称
	 */
	@TableField("org_name")
	private String orgName;

	/**
	 * 机构类型
	 */
	@TableField("org_type_id")
	private Long orgTypeId;

    /**
     * 树结构
     */
    @TableField("tree_str")
    private String treeStr;

	/**
	 * 排序
	 */
	@TableField("sort")
	private Integer sort;

	/**
	 * 是否删除 0：正常 1：已删除
	 */
	@TableField("del_flag")
	private Integer delFlag;

	/**
	 * 申请信息ID
	 */
	@TableField("apply_id")
	private Long applyId;

	/**
	 * 机构状态
	 */
	@TableField("state")
	private Integer state;

	/**
	 * 认证状态  0未认证 ； 1认证中； 2 认证成功；3认证失败
	 */
	@TableField("approve_flag")
	private Integer approveFlag;

	/**
	 * 子级机构
	 */
	@TableField(exist = false)
	private List<SysOrgModel> children = new ArrayList<>();

}
