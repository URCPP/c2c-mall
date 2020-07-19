package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 *
 * @author cjunyuan
 * @date 2019/2/26 20:52
 */
@Data
@TableName("biz_payment_pic")
public class BizPaymentPicModel extends BaseModel {
    
    private static final long serialVersionUID = -4253522129795669224L;

    @TableField("pay_type")
    private Integer payType;

    @TableField("apply_id")
    private Long applyId;

    @TableField("pic")
    private String pic;
}
