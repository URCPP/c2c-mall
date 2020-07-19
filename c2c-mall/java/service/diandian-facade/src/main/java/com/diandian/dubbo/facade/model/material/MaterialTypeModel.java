package com.diandian.dubbo.facade.model.material;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

@Data
@TableName("material_type")
public class MaterialTypeModel extends BaseModel {

    /**
     * 素材id
     */
    @TableField("material_id")
    private Long materialId;


    /**
     * 类型名称
     */
    @TableField("type_name")
    private String typeName;


    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;
}
