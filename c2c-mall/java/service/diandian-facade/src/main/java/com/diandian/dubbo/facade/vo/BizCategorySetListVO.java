package com.diandian.dubbo.facade.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author jbuhuan
 * @Date 2019/3/11 22:15
 */
@Data
public class BizCategorySetListVO implements Serializable {

    private  Long id;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 类别类别(0 一级; 1 二级)
     */
    private Integer categoryTypeFlag;

    /**
     * 父类别( 一级类别的父ID为0  )
     */
    private Long parentCategoryId;

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
