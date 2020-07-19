package com.diandian.dubbo.facade.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author cjunyuan
 * @since 2019-05-07
 */
@Data
@TableName("product_brand")
public class ProductBrandModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 品牌名称
     */
    @TableField("brand_name")
    private String brandName;

    /**
     * 品牌图片地址
     */
    @TableField("brand_img_url")
    private String brandImgUrl;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

}
