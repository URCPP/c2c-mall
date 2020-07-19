package com.diandian.dubbo.facade.model.merchant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

@Data
@TableName("merchant_collect")
public class MerchantCollectModel extends BaseModel {

    @TableField("material_id")
    private Long materialId;


    @TableField("merchant_id")
    private Long merchantId;



}
