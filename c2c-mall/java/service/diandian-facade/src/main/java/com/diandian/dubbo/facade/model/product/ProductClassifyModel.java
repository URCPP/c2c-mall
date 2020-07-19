package com.diandian.dubbo.facade.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 16:19 2019/11/1
 * @Modified By:
 */
@Data
@TableName("product_classify")
public class ProductClassifyModel extends BaseModel {

    /**
     * 分类名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 上级分类
     */
    @TableField("category_parent")
    private Long categoryParent;

    /**
     * 父级菜单名
     */
    @TableField(exist = false)
    private String parentCategoryName;



    /**
     * 分类类型
     */
    @TableField("category_type")
    private Integer categoryType;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 删除标记 0未删除 1已删除
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 展开标识
     */
    @TableField(exist = false)
    private Boolean open;


}
