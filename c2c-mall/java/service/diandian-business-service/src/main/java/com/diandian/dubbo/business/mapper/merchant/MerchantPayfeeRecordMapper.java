package com.diandian.dubbo.business.mapper.merchant;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.merchant.MerchantPayFeeRecordQueryDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantPayfeeRecordModel;
import com.diandian.dubbo.facade.vo.StatisticsByDayVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantPayfeeRecordVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 商户支付表
 *
 * @author zweize
 * @date 2019/03/07
 */
public interface MerchantPayfeeRecordMapper extends BaseMapper<MerchantPayfeeRecordModel> {

    /**
     *
     * 功能描述: 按天统计充值费用或者续费费用
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/7 21:39
     */
    List<StatisticsByDayVO> statisticsByDayVO(@Param("dto") MerchantPayFeeRecordQueryDTO dto);

    /**
     *
     * 功能描述: 统计总的充值费用或者续费费用
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/4/15 17:25
     */
    Map<String, Object> statisticsTotalFee();

    /**
     *
     * 功能描述: 分页查询商户支付明细
     *
     * @param page
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/4/22 16:37
     */
    IPage<MerchantPayfeeRecordVO> listForPage(Page<MerchantPayfeeRecordVO> page, @Param("dto") MerchantPayFeeRecordQueryDTO dto);
}
