package com.diandian.dubbo.business.mapper.transport;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.transport.TransportFeeRuleAreaModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 3运输方式计费规则区域关系 Mapper 接口
 * </p>
 *
 * @author zzj
 * @since 2019-02-28
 */
public interface TransportFeeRuleAreaMapper extends BaseMapper<TransportFeeRuleAreaModel> {
    void insertBatch(@Param("list") List<TransportFeeRuleAreaModel> list);

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
    Integer count(@Param("transportRuleId") Long transportRuleId, @Param("regionCode") String regionCode);
}
