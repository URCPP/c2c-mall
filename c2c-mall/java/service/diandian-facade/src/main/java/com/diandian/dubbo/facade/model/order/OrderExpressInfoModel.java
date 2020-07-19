package com.diandian.dubbo.facade.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 订单快递信息表
 * </p>
 *
 * @author cjunyuan
 * @since 2019-05-15
 */
@Data
@TableName("order_express_info")
public class OrderExpressInfoModel extends BaseModel {

    private static final long serialVersionUID = 5724801189454134681L;

    /**
     * 运输单号
     */
    @TableField("transport_no")
    private String transportNo;

    /**
     * 运输公司编码
     */
    @TableField("transport_code")
    private String transportCode;

    /**
     * 快递状态（0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单）
     */
    @TableField("state")
    private Integer state;

    /**
     * 内容
     */
    @TableField("context")
    private String context;

    /**
     * 时间，原始格式
     */
    @TableField("time")
    private String time;

    /**
     * 格式化后时间
     */
    @TableField("ftime")
    private String ftime;

    /**
     * 本数据元对应的签收状态。只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现
     */
    @TableField("status")
    private String status;

    /**
     * 本数据元对应的行政区域的编码，只有在开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现
     */
    @TableField("area_code")
    private String areaCode;

    /**
     * 本数据元对应的行政区域的名称，开通签收状态服务（见上面"status"后的说明）且在订阅接口中提交resultv2标记后才会出现
     */
    @TableField("area_name")
    private String areaName;

}
