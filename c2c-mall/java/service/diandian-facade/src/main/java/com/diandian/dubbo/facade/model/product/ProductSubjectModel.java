package com.diandian.dubbo.facade.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * 产品专题
 *
 * @author zzhihong
 * @date 2019/02/28
 */
@Data
@TableName("product_subject")
public class ProductSubjectModel extends BaseModel {

    private static final long serialVersionUID = 1L;


    /**
     * 专题名称
     */
    @TableField("subject_name")
    private String subjectName;


    /**
     * 图片URL
     */
    @TableField("image_url")
    private String imageUrl;

    /**
     * 删除标识 0未删除 1已删除
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;


}
