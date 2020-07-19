package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 商城新闻表
 *
 * @author zweize
 * @date 2019/02/28
 */
@Data
@TableName("biz_mall_news")
public class BizMallNewsModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 标题
	 */
    @TableField("title")
	private String title;
 
 	/**
	 * 作者
	 */
    @TableField("author")
	private String author;
 
 	/**
	 * 来源
	 */
    @TableField("source")
	private String source;
 
 	/**
	 * 内容
	 */
    @TableField("content")
	private String content;
 
 	/**
	 * 是否显示0不显示1显示
	 */
    @TableField("is_show")
	private Integer isShow;
 
 	/**
	 * 排序
	 */
    @TableField("sort_num")
	private Integer sortNum;


	/**
	 * 图标
	 */
	@TableField("pic")
	private String pic;
 
}
