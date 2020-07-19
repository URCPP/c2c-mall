package com.diandian.dubbo.facade.model.transport;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 1运输方式信息
 * </p>
 *
 * @author zzj
 * @since 2019-02-28
 */
@Data
@TableName("transport_info")
public class TransportInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 运输方式ID
     */
    @TableField("transport_company_id")
    private Long transportCompanyId;


    /**
     * 运输方式名称
     */
    private String transportName;

    /**
     * 仓库ID
     */
    private Long repositoryId;

    /**
     * 运输类型（0：物流快递 1：仓库自提 2：包邮）
     */
    private Integer transportType;

    /**
     * 计费类型 0按件 1按重量 2按体积
     */
    private Integer feeType;

    /**
     * 费用介绍
     */
    private String feeIntroduce;

    /**
     * 友情提示
     */
    private String tips;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标识0未删除 1已删除
     */
    private Integer delFlag;


    /**
     * 运输费用规则list
     */
    @TableField(exist = false)
    private List<TransportFeeRuleModel> transportFeeRuleList;

    /**
     * 运输方式名称
     */
    @TableField(exist = false)
    private String transportCompanyName;

    /**
     * 仓库名称
     */
    @TableField(exist = false)
    private String repositoryName;

    /**
     * 店铺id
     */
    @TableField("shop_id")
    private Long shopId;

}
