package com.diandian.dubbo.facade.service.sys;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.api.query.GetAreaDTO;
import com.diandian.dubbo.facade.dto.api.query.GetAreaListDTO;
import com.diandian.dubbo.facade.dto.api.result.AreaListResultDTO;
import com.diandian.dubbo.facade.model.sys.SysPlaceRegionModel;

import java.util.List;

/**
 * <p>
 * 全国区县 服务类
 * </p>
 *
 * @author zzj
 * @since 2019-02-28
 */
public interface SysPlaceRegionService {
    List<SysPlaceRegionModel> list();

    /**
     *
     * 功能描述: 地区分页查询
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/5/13 17:57
     */
    PageResult apiListPage(GetAreaListDTO dto);

    /**
     *
     * 功能描述: 根据code获得名称
     *
     * @param code
     * @return
     * @author cjunyuan
     * @date 2019/5/14 19:08
     */
    String getNameByCode(Integer code);

    /**
     *
     * 功能描述: 根据code获取单个对象
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/7/26 14:22
     */
    AreaListResultDTO getOne(GetAreaDTO dto);
}
