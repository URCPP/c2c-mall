package com.diandian.dubbo.facade.service.transport;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.transport.TransportInfoModel;
import com.diandian.dubbo.facade.vo.TransportInfoVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 1运输方式信息 服务类
 * </p>
 *
 * @author zzj
 * @since 2019-02-28
 */
public interface TransportInfoService{

    PageResult listPage(Map<String, Object> params);

    TransportInfoModel getById(Long id);

    void updateTransportInfo(TransportInfoModel transportInfoModel);

    void saveTransportInfo(TransportInfoModel transportInfoModel);

    void deleteTransportInfo(Long id);

    List<TransportInfoModel> listDetail(List<Long> transportIds);
    /**
     * @Author wubc
     * @Description // 根据类型查询
     * @Date 18:20 2019/3/20
     * @Param [express]
     * @return java.util.List<com.diandian.dubbo.facade.model.transport.TransportInfoModel>
     **/
    List<TransportInfoModel> listByType(Integer type);

    /**
     *
     * 功能描述: 查询某条规则下的地址编辑是否存在
     *
     * @param transportRuleId
     * @param regionCode
     * @return
     * @author cjunyuan
     * @date 2019/4/26 10:33
     */
    Integer checkRuleIncludeRegionCode(Long transportRuleId, String regionCode);

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
    List<TransportInfoVO> getTransportVOList(String idsStr, List<Integer> transportTypeIds);
}
