package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 系统公告表
 *
 * @author jbh
 * @date 2019/02/26
 */
@Data
@TableName("biz_notify_info")
public class BizNotifyInfoModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 标题
	 */
    @TableField("title")
	private String title;
 
 	/**
	 * 状态（0正常，1禁用）
	 */
    @TableField("state_flag")
	private Integer stateFlag;
 
 	/**
	 * 类型  0 系统公告；
	 */
    @TableField("type_flag")
	private Integer typeFlag;
 
 	/**
	 * 是否最新（0 否，1是）
	 */
    @TableField("new_flag")
	private Integer newFlag;
 
 	/**
	 * 公告内容
	 */
    @TableField("content")
	private String content;
 
 
 
 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;
 
}
