package com.diandian.dubbo.facade.vo.merchant;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: byp
 * @Description:
 * @Date: Created in 14:41 2019/9/26
 * @Modified By:
 */
@Data
public class MerchantShopClassifyVo implements Serializable {

    private  Long id;

    /**
     * 类别名称
     */
    private String classifyName;

    /**
     * 类别类别(0 一级; 1 二级)
     */
    private Integer categoryType;

    /**
     * 父类别( 一级类别的父ID为0  )
     */
    private Long parentCategory;

    /**
     * 父类别名称
     */
    private String parentCategoryName;

    /**
     * 排序号
     */
    private Integer sort;


    private Date createTime;
}
