package com.diandian.dubbo.facade.vo.merchant;

import com.diandian.dubbo.facade.model.product.ProductInfoModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wubc
 * @date 2019/2/27 17:10
 */
@Data
public class MerchantPurchaseOrderVO implements Serializable {
    /**
     * 总订单
     */
    private Integer allNum =10;

    /**
     * 待付款 0
     */
    private Integer obligationNum;

    /**
     * 待发货 1
     */
    private Integer overhangNum;

    /**
     * 己发货 2
     */
    private Integer shipmentsNum;

    /**
     * 己取消 98
     */
    private Integer cancelNum;

    /**
     * 己完成 3
     */
    private Integer finishNum;
}
