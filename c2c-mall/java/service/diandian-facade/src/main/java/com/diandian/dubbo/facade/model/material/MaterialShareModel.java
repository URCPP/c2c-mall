package com.diandian.dubbo.facade.model.material;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

@Data
@TableName("material_share")
public class MaterialShareModel extends BaseModel {
    private static final long serialVersionUID = 1L;

    /**
     * 商户id
     */

    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 素材id
     */
    @TableField("material_id")
    private Long materialId;
}
