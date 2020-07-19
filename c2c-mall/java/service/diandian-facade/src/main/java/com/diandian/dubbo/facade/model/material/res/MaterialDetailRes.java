package com.diandian.dubbo.facade.model.material.res;

import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-13 20:58
 */
@Data
public class MaterialDetailRes extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Date createTime;

    private Date updateTime;

    private Long merchantId;

    private String merchantName;

    private String avatar;

    private Long productId;

    private Long materialTypeId;

    private String shareContent;

    private String materialImg;

    private String comment;

    private Integer attentionFlag;

    private Integer collectState;

    private Integer likeNum;

    private Integer pageview;

    private Integer shareNum;

    private String productName;

    private BigDecimal price;

    private String[] imgUrls;

    private String coverMap;

}