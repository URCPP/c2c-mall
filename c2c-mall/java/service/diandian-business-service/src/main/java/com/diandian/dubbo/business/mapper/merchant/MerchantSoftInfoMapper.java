package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.dto.merchant.MerchantSoftInfoDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantSoftInfoModel;
import com.diandian.dubbo.facade.vo.merchant.MerchantSoftInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 商户软件信息表
 *
 * @author wbc
 * @date 2019/02/19
 */
public interface MerchantSoftInfoMapper extends BaseMapper<MerchantSoftInfoModel> {

    /**
     * 根据商户的id查询商户的软件信息
     * @param merchantId
     * @return
     */
    List<MerchantSoftInfoVO> listByMerchantId(@Param("merchantId") Long merchantId);

    /**
     *
     * 功能描述: 查询商户的软件权益信息（管理员手动修改用）
     *
     * @param merchantId
     * @return
     * @author cjunyuan
     * @date 2019/3/25 18:05
     */
    List<MerchantSoftInfoDTO> queryMerchantSoftInfo(@Param("merchantId") Long merchantId);

}
