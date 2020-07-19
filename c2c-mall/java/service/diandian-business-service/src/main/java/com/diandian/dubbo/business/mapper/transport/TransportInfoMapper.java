package com.diandian.dubbo.business.mapper.transport;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.transport.TransportInfoModel;
import com.diandian.dubbo.facade.vo.TransportInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 1运输方式信息 Mapper 接口
 * </p>
 *
 * @author zzj
 * @since 2019-02-28
 */
public interface TransportInfoMapper extends BaseMapper<TransportInfoModel> {
    TransportInfoModel getById(@Param("id") Long id);
    List<TransportInfoModel> listDetail(@Param("transportIds") List<Long> transportIds);

    /**
     *
     * 功能描述: 获取商品的运输方式
     *
     * @param idsStr
     * @param transportTypeIds
     * @return
     * @author cjunyuan
     * @date 2019/4/22 20:15
     */
    List<TransportInfoVO> getTransportVOList(@Param("idsStr") String idsStr, @Param("typeIds") List<Integer> transportTypeIds);
}
