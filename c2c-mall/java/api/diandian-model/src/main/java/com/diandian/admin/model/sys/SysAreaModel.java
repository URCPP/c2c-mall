package com.diandian.admin.model.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.admin.model.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 地区码表
 *
 * @author x
 * @date 2018/09/26
 */
@Getter
@Setter
@ToString
@TableName("sys_area")
public class SysAreaModel extends BaseModel {

	private static final long serialVersionUID = 1L;



 	/**
	 * 地区编码
	 */
    @TableField("areaCode")
	private String areaCode;

 	/**
	 * 地区名
	 */
    @TableField("areaName")
	private String areaName;

 	/**
	 * 地区级别（1:省份province,2:市city,3:区县district,4:街道street）
	 */
    @TableField("areaLevel")
	private Integer areaLevel;

 	/**
	 * 城市编码
	 */
    @TableField("cityCode")
	private String cityCode;

 	/**
	 * 城市中心点（即：经纬度坐标）
	 */
    @TableField("center")
	private String center;

 	/**
	 * 地区父节点
	 */
    @TableField("parentId")
	private Integer parentId;



}
