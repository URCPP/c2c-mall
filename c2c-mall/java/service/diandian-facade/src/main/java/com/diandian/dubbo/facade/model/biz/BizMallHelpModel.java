package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 商城图文配置表
 *
 * @author zweize
 * @date 2019/02/27
 */
@Data
@TableName("biz_mall_help")
public class BizMallHelpModel extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 标题
	 */
	@TableField("title")
	private String title;

 	/**
	 * 0目录1文章
	 */
    @TableField("type")
	private Integer type;
 
 	/**
	 * 上级ID
	 */
    @TableField("parent_id")
	private Long parentId;
 
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

	/**
	 * 上级目录名称
	 */
	@TableField(exist = false)
    private String parentName;
 
}
