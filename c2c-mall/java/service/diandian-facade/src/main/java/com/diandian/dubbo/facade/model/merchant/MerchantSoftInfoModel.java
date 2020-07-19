package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 商户软件信息表
 *
 * @author wbc
 * @date 2019/02/19
 */
@Data
@TableName("merchant_soft_info")
public class MerchantSoftInfoModel extends BaseModel {

	private static final long serialVersionUID = 1L;


 
 	/**
	 * 商户ID
	 */
    @TableField("merchant_id")
	private Long merchantId;
 
 	/**
	 * 软件类型ID
	 */
    @TableField("soft_type_id")
	private Long softTypeId;
 
 	/**
	 * 可用开通数量
	 */
    @TableField("available_open_num")
	private Integer availableOpenNum;
 
 	/**
	 * 己开通数量
	 */
    @TableField("open_num")
	private Integer openNum;
 
 
 
 	/**
	 * 
	 */
    @TableField("remark")
	private String remark;
 
}
