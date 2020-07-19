package com.diandian.dubbo.facade.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 产品类目
 *
 * @author zzhihong
 * @date 2019/02/15
 */
@Data
@TableName("product_category")
public class ProductCategoryModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 父级ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 父级菜单名
     */
    @TableField(exist = false)
    private String parentCategoryName;

    /**
     * 分类名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 删除标记 0未删除 1已删除
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * +
     * 图片URL
     */
    @TableField("image_url")
    private String imageUrl;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 显示在导航条 0不显示 1显示
     */
    @TableField("show_nav")
    private Integer showNav;

    /**
     * 商品抽点
     */
    @TableField("product_commission")
    private BigDecimal productCommission;

    /**
     * 展开标识
     */
    @TableField(exist = false)
    private Boolean open;


}
