package com.diandian.dubbo.facade.model.material;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
@TableName("material_detail")
public class MaterialDetailModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 商户id
     */

    @TableField("merchant_id")
    private Long merchantId;

    /**
     * 商品id
     */

    @TableField("product_id")
    private Long productId;


    @TableField(exist = false)
    private String productName;

    @TableField(exist = false)
    private String imageUrls;

    @TableField(exist = false)
    private BigDecimal price;

    @TableField(exist = false)
    private Integer news;

    @TableField(exist = false)
    private Integer me;

    /**
     * 素材类型id  默认综合
     */

    @TableField("material_type_id")
    private Long materialTypeId;

    /**
     * 商户名
     */
    @TableField(exist = false)
    private String userName;


    @TableField(exist = false)
    private String avatar;

    /**
     * 发圈内容
     */
    @TableField("share_content")
    private String shareContent;


    /**
     *素材图片
     */
    @TableField("material_img")
    private String materialImg;

    /**
     * 评论
     */
    @TableField("comment")
    private String comment;




    /**
     * 关注标识
     */

    @TableField("attention_flag")
    private Integer attentionFlag;



    /**
     * 收藏状态  0-未收藏  1-已收藏
     */

    @TableField("collect_state")
    private Integer collectState;


    /**
     * 点赞数
     */

    @TableField("like_num")
    private Integer likeNum;

    /**
     * 阅读数
     */
    @TableField("pageview")
    private Integer pageview;

    /**
     * 分享数
     */
    @TableField("share_num")
    private Integer shareNum;


    @TableField(exist = false)
    private Integer image;


    @TableField(exist = false)
    private Integer collect;


    @TableField(exist = false)
    private String [] imgUrl;

}
