package com.diandian.dubbo.facade.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@TableName("sys_org_type_menu")
public class SysOrgTypeMenuModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	@TableField("org_type_id")
	private Long orgTypeId;

	@TableField("menu_id")
	private Long menuId;

}
