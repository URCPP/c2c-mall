package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:财务记录详情
 * @author: wsk
 * @create: 2019-09-03 15:07
 */
@Data
@TableName("biz_financial_records_detail")
public class BizFinancialRecordsDetailModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 删除标识
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 店铺id
     */
    @TableField("shop_id")
    private Long shopId;

    /**
     * 商品id
     */
    @TableField("product_id")
    private Long productId;

    /**
     * 状态
     */
    @TableField("state")
    private Integer state;

    /**
     * 类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 金额
     */
    @TableField("money")
    private BigDecimal money;

}