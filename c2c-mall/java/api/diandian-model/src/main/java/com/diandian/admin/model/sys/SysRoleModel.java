package com.diandian.admin.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.admin.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 角色
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:29
 */
@Getter
@Setter
@ToString
@TableName("sys_role")
public class SysRoleModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;


    @TableField("create_by")
    private Long createBy;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 机构ID
     */
    @TableField("org_id")
    private Long orgId;

    /**
     * 机构名称
     */
    @TableField(exist = false)
    private String orgName;

    /**
     * 菜单ID_LIST
     */
    @TableField(exist = false)
    private List<Long> menuIdList;

    /**
     * 菜单列表
     */
    @TableField(exist = false)
    private List<SysMenuModel> menuList;


}
