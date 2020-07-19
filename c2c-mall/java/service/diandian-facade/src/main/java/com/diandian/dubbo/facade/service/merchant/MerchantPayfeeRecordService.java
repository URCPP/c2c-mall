package com.diandian.dubbo.facade.service.merchant;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.merchant.MerchantPayFeeRecordQueryDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantPayfeeRecordModel;
import com.diandian.dubbo.facade.vo.StatisticsByDayVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantPayfeeRecordVO;

import java.util.List;
import java.util.Map;

/**
 * 商户支付表
 *
 * @author zweize
 * @date 2019/03/07
 */
public interface MerchantPayfeeRecordService {

    void save(MerchantPayfeeRecordModel merchantPayfeeRecordModel);

    MerchantPayfeeRecordModel getByPayNo(String payNo);

    //void paySuccess(MerchantPayfeeRecordModel merchantPayfeeRecordModel);

    /**
     *
     * 功能描述: 按天统计充值费用或者续费费用
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/7 21:39
     */
    List<StatisticsByDayVO> statisticsByDayVO(MerchantPayFeeRecordQueryDTO dto);

    /**
     *
     * 功能描述: 统计总的充值费用或者续费费用
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/4/15 17:30
     */
    Map<String, Object> statisticsTotalFee();

    /**
     *
     * 功能描述: 分页查询商户支付明细
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/4/22 16:37
     */
    PageResult listForPage(MerchantPayFeeRecordQueryDTO dto);
}
