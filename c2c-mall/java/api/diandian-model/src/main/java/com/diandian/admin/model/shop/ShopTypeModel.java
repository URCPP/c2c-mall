package com.diandian.admin.model.shop;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.admin.model.BaseModel;
import lombok.Data;

/**
 * 店铺类型
 *
 * @author zzhihong
 * @date 2019/02/26
 */
@Data
@TableName("shop_type")
public class ShopTypeModel extends BaseModel {

	private static final long serialVersionUID = 1L;



 	/**
	 * 类型名称
	 */
    @TableField("type_name")
	private String typeName;

 	/**
	 * 删除标识0未删除 1已删除
	 */
    @TableField("del_flag")
	private Integer delFlag;

 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;



}
