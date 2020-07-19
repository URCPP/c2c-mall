package com.diandian.admin.model.transport;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.admin.model.BaseModel;
import lombok.Data;

/**
 * 运输公司
 *
 * @author zzhihong
 * @date 2019/02/28
 */
@Data
@TableName("transport_company")
public class TransportCompanyModel extends BaseModel {

	private static final long serialVersionUID = 1L;



 	/**
	 * 运输公司名称
	 */
    @TableField("transport_company_name")
	private String transportCompanyName;

 	/**
	 * 联系人
	 */
    @TableField("contact_name")
	private String contactName;

 	/**
	 * 联系电话
	 */
    @TableField("contact_phone")
	private String contactPhone;

 	/**
	 * 联系地址
	 */
    @TableField("address")
	private String address;

 	/**
	 * 删除标识 0未删除 1已删除
	 */
    @TableField("del_flag")
	private Integer delFlag;

 	/**
	 * 备注
	 */
    @TableField("remark")
	private String remark;

	/**
	 * 店铺id
	 */
	@TableField("shop_id")
	private String shopId;



}
