package com.diandian.dubbo.facade.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * <p>
 * 订单和快递信息关系表
 * </p>
 *
 * @author cjunyuan
 * @since 2019-06-04
 */
@Data
@TableName("order_detail_transport")
public class OrderDetailTransportModel extends BaseModel {

    private static final long serialVersionUID = 4353709944995191796L;

    /**
     * 订单详情ID
     */
    @TableField("order_detail_id")
    private Long orderDetailId;

    /**
     * 运输单号
     */
    @TableField("transport_no")
    private String transportNo;

    /**
     * 运输公司code
     */
    @TableField("transport_code")
    private String transportCode;

    /**
     * 运输单状态（polling:监控中，shutdown:结束，abort:中止，updateall：重新推送）
     */
    @TableField("transport_status")
    private String transportStatus;

}
