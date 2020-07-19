package com.diandian.dubbo.facade.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 产品类目属性名
 *
 * @author zzhihong
 * @date 2019/02/18
 */
@Data
@TableName("product_attr_name")
public class ProductAttrNameModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 类目ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 类目名
     */
    @TableField(exist = false)
    private String categoryName;

    /**
     * 属性名
     */
    @TableField("attr_name")
    private String attrName;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * sku标识(0否 1是)
     */
    @TableField("sku_flag")
    private Integer skuFlag;

    /**
     * 删除标记 0未删除 1已删除
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


}
