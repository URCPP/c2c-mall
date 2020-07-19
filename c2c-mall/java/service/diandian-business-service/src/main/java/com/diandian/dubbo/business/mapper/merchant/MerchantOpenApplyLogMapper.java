package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.merchant.MerchantOpenApplyLogQueryDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantOpenApplyLogModel;
import com.diandian.dubbo.facade.vo.merchant.MerchantOpenApplyLogDetailVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantOpenApplyLogListVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * 商户软件开通申请记录表
 *
 * @author wbc
 * @date 2019/02/20
 */
public interface MerchantOpenApplyLogMapper extends BaseMapper<MerchantOpenApplyLogModel> {

    /**
     * 功能描述: 分页查询商户列表信息
     *
     * @param page
     * @param params
     * @return
     * @author cjunyuan
     * @date 2018/12/12 10:44
     */
    IPage<MerchantOpenApplyLogListVO> softApplyInfoListPage(Page<MerchantOpenApplyLogListVO> page, @Param(value = "params") MerchantOpenApplyLogQueryDTO params);

    /**
     *
     * 功能描述: 查询商户申请详情
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/2/28 10:30
     */
    MerchantOpenApplyLogDetailVO getMerchantApplyDetail(@Param("dto") MerchantOpenApplyLogQueryDTO dto);

    /**
     *
     * 功能描述: 计算数量
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/7 17:40
     */
    Integer count(@Param("dto") MerchantOpenApplyLogQueryDTO dto);

}
