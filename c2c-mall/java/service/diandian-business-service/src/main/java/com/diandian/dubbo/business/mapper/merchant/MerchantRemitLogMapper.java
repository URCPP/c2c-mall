package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.model.merchant.MerchantRemitLogModel;
import com.diandian.dubbo.facade.vo.merchant.MerchantRemitLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * 商户汇款记录明细表
 *
 * @author jbh
 * @date 2019/03/29
 */
public interface MerchantRemitLogMapper extends BaseMapper<MerchantRemitLogModel> {

    IPage<MerchantRemitLogModel> listPage(Page page, @Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 获取开通商城列表
     *
     * @param page
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/4/22 21:07
     */
    IPage<MerchantRemitLogVO> listMallOpenPage(Page page, @Param("params") Map<String, Object> params);
}
