package com.diandian.dubbo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.sys.SysOrgTypeMenuModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构管理
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:28
 */
public interface SysOrgTypeMenuMapper extends BaseMapper<SysOrgTypeMenuModel> {

    /**
     *
     * 功能描述: 根据机构类型ID查询对应的菜单ID列表
     *
     * @param orgTypeId
     * @return
     * @author cjunyuan
     * @date 2019/2/17 16:42
     */
    List<Long> queryMenuIdListByOrgTypeId(@Param("orgTypeId") Long orgTypeId);
}
