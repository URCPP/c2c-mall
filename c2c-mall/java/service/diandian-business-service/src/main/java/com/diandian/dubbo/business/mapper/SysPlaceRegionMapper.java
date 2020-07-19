package com.diandian.dubbo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.api.query.GetAreaListDTO;
import com.diandian.dubbo.facade.dto.api.result.AreaListResultDTO;
import com.diandian.dubbo.facade.model.sys.SysPlaceRegionModel;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 全国区县 Mapper 接口
 * </p>
 *
 * @author zzj
 * @since 2019-02-28
 */
public interface SysPlaceRegionMapper extends BaseMapper<SysPlaceRegionModel> {


    /**
     *
     * 功能描述: 分页查询机构列表
     *
     * @param page
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/14 18:03
     */
    Page<AreaListResultDTO> apiListPage(Page<AreaListResultDTO> page, @Param("dto") GetAreaListDTO dto);

    /**
     *
     * 功能描述: 根据code获得名称
     *
     * @param code
     * @return
     * @author cjunyuan
     * @date 2019/5/14 19:08
     */
    String getNameByCode(@Param("code") Integer code);

    /**
     *
     * 功能描述: 根据code获取单个对象
     *
     * @param code
     * @return
     * @author cjunyuan
     * @date 2019/7/26 14:22
     */
    AreaListResultDTO getOne(Integer code);
}
