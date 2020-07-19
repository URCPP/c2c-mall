package com.diandian.dubbo.facade.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jbuhuan
 * @date 2019/2/22 14:12
 */
@Data
public class BizCategorySetVO implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 父类别
     */
    private Long parentCategoryId;
}
