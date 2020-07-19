package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 商城成功案例表
 *
 * @author zweize
 * @date 2019/03/06
 */
@Data
@TableName("biz_mall_success_case")
public class BizMallSuccessCaseModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 名称
	 */
    @TableField("title")
	private String title;
 
 	/**
	 * 0目录1案例
	 */
    @TableField("type")
	private Integer type;
 
 	/**
	 * 目录
	 */
    @TableField("parent_id")
	private Long parentId;
 
 	/**
	 * 图标
	 */
    @TableField("pic")
	private String pic;
 
 	/**
	 * 内容
	 */
    @TableField("content")
	private String content;
 
 	/**
	 * 是否显示 0不显示，1显示
	 */
    @TableField("is_show")
	private Integer isShow;
 
 	/**
	 * 排序
	 */
    @TableField("sort_num")
	private Integer sortNum;

    @TableField(exist = false)
	private String parentName;
 
}
