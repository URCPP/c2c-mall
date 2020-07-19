package com.diandian.dubbo.facade.model.res;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 资源分组
 *
 * @author zzhihong
 * @date 2019/02/19
 */
@Data
@TableName("res_group")
public class ResGroupModel extends BaseModel {

	private static final long serialVersionUID = 1L;


	/**
	 * 资源类型ID
	 */
	@TableField("res_type")
	private String resType;

 	/**
	 * 分组名称
	 */
    @TableField("group_name")
	private String groupName;

 	/**
	 * 排序号
	 */
    @TableField("sort")
	private Integer sort;



 	/**
	 * 创建人
	 */
    @TableField("create_by")
	private Long createBy;

 	/**
	 * 更新人
	 */
    @TableField("update_by")
	private Long updateBy;

}
