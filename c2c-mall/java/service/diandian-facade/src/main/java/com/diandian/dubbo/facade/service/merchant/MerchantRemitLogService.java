package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.merchant.MerchantRemitDTO;
import com.diandian.dubbo.facade.dto.merchant.RemitAuditDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantRemitLogModel;

import java.util.Map;

/**
 * 商户汇款记录明细表
 *
 * @author jbh
 * @date 2019/03/29
 */
public interface MerchantRemitLogService {

    /**
     * @return boolean
     * @Author wubc
     * @Description // 订单添加
     * @Date 18:55 2019/3/29
     * @Param [dto]
     **/
    String addRemit(MerchantRemitDTO dto);

    /**
     * @return boolean
     * @Author wubc
     * @Description // 上传凭证
     * @Date 20:36 2019/3/29
     * @Param [dto]
     **/
    boolean updRemit(MerchantRemitDTO dto);

    /**
     * @return boolean
     * @Author wubc
     * @Description // 储备金退还
     * @Date 11:11 2019/3/30
     * @Param [merchantInfoId]
     **/
    boolean sendBack(Long merchantInfoId);

    /**
     * @return com.diandian.dubbo.facade.model.merchant.MerchantRemitLogModel
     * @Author wubc
     * @Description // 获取商户开通或退还订单
     * @Date 12:45 2019/3/30
     * @Param [merchantInfoId]
     **/
    MerchantRemitLogModel getRemitOrder(Long merchantInfoId);

    /**
     * @return com.diandian.dubbo.facade.dto.PageResult
     * @Author wubc
     * @Description // 打款列表
     * @Date 7:15 2019/3/31
     * @Param [params]
     **/
    PageResult listPage(Map<String, Object> params);

    MerchantRemitLogModel getById(Long id);

    /**
     * @return boolean
     * @Author wubc
     * @Description // 打款审核
     * @Date 20:42 2019/3/31
     * @Param [dto]
     **/
    //boolean audit(RemitAuditDTO dto);

    /**
     * @Author wubc
     * @Description // 取消退款订单
     * @Date 16:42 2019/4/1
     * @Param [merchantInfoId]
     * @return boolean
     **/
    boolean cancelRemit(Long merchantInfoId);

    /**
     *
     * 功能描述: 获取开通商城列表
     *
     * @param params
     * @return 
     * @author cjunyuan
     * @date 2019/4/22 21:07
     */
    PageResult listMallOpenPage(Map<String, Object> params);
}
