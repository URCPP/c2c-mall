package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 行业类别设置表
 *
 * @author jbh
 * @date 2019/02/22
 */
@Data
@TableName("biz_category_set")
public class BizCategorySetModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 类别名称
	 */
    @TableField("category_name")
	private String categoryName;
 
 	/**
	 * 类别类别(0 一级; 1 二级)
	 */
    @TableField("category_type_flag")
	private Integer categoryTypeFlag;
 
 	/**
	 * 父类别( 一级类别的父ID为0  )
	 */
    @TableField("parent_category_id")
	private Long parentCategoryId;
 
 	/**
	 * 排序号
	 */
    @TableField("sort")
	private Integer sort;
 
 
 
}
