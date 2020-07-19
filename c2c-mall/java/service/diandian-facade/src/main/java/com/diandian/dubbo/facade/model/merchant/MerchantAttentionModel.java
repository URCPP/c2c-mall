package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

@Data
@TableName("merchant_attention")
public class MerchantAttentionModel extends BaseModel {

    /**
     * 商户id
     */
    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 被关注人id
     */
    @TableField("focus_merchant_id")
    private Long focusMerchantId;


    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String avatar;

    @TableField(exist = false)
    private Integer isFollow;

    /**
     * 关注关系  默认是0 未关注  1-已关注 2-互相关注  3-被关注
     */
    @TableField("state")
    private Integer state;

}
