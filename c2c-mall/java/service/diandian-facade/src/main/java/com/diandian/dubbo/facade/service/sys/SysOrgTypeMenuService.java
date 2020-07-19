package com.diandian.dubbo.facade.service.sys;

import java.util.List;

/**
 * 机构管理
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:28
 */
public interface SysOrgTypeMenuService {

    /**
     *
     * 功能描述: 保存机构类型和菜单的关系
     *
     * @param orgTypeId
     * @param menuIdList
     * @return
     * @author cjunyuan
     * @date 2019/2/17 16:39
     */
    void saveOrgMenu(Long orgTypeId, List<Long> menuIdList);

    /**
     *
     * 功能描述: 根据机构类型ID查询菜单的ID列表
     *
     * @param orgTypeId
     * @return
     * @author cjunyuan
     * @date 2019/2/17 17:18
     */
    List<Long> queryMenuIdListByOrgTypeId(long orgTypeId);

    /**
     *
     * 功能描述: 统计数量
     *
     * @param menuId
     * @param orgTypeId
     * @return
     * @author cjunyuan
     * @date 2019/3/15 16:36
     */
    Integer count(Long menuId, Long orgTypeId);
}

