package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:财务记录
 * @author: wsk
 * @create: 2019-08-31 16:12
 */
@Data
@TableName("biz_financial_records")
public class BizFinancialRecordsModel extends BaseModel {

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
     * 收入
     */
    @TableField("income")
    private BigDecimal income;

    /**
     * 支出
     */
    @TableField("expenditure")
    private BigDecimal expenditure;

    /**
     * 收入交易数量
     */
    @TableField("income_count")
    private Integer incomeCount;

    /**
     * 支出交易数量
     */
    @TableField("expenditure_count")
    private Integer expenditureCount;

}