package com.diandian.admin.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.admin.model.BaseModel;
import lombok.Data;


/**
 * 机构打款凭证表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Data
@TableName("biz_payment_pic")
public class BizPaymentPicModel extends BaseModel {

	private static final long serialVersionUID = 1L;

 
 	/**
	 * 打款类型( 0开通机构打款， 1提现打款)
	 */
    @TableField("apply_type")
	private Integer applyType;
 
 	/**
	 * 申请ID
	 */
    @TableField("apply_id")
	private Long applyId;
 
 	/**
	 * 打款凭证图片
	 */
    @TableField("pic")
	private String pic;
 
}
