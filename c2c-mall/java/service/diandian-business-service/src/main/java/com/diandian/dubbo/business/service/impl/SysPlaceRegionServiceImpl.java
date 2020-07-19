package com.diandian.dubbo.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.SysPlaceRegionMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.api.query.GetAreaDTO;
import com.diandian.dubbo.facade.dto.api.query.GetAreaListDTO;
import com.diandian.dubbo.facade.dto.api.result.AreaListResultDTO;
import com.diandian.dubbo.facade.model.sys.SysPlaceRegionModel;
import com.diandian.dubbo.facade.service.sys.SysPlaceRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 全国区县 服务实现类
 * </p>
 *
 * @author zzj
 * @since 2019-02-28
 */
@Service("sysPlaceRegionService")
public class SysPlaceRegionServiceImpl implements SysPlaceRegionService {

    @Autowired
    private SysPlaceRegionMapper sysPlaceRegionMapper;

    @Override
    public List<SysPlaceRegionModel> list() {
        String excludeCode = "71";
        return sysPlaceRegionMapper.selectList(
                new LambdaQueryWrapper<SysPlaceRegionModel>()
                        .ne(SysPlaceRegionModel::getRegionCode,excludeCode)
                        .ne(SysPlaceRegionModel::getParentRegionCode,excludeCode));
    }

    @Override
    public PageResult apiListPage(GetAreaListDTO dto) {
        Map<String, Object> params = new HashMap<>();
        if(null != dto.getPage()){
            params.put("curPage", dto.getPage().toString());
        }
        if(null != dto.getPageSize()){
            params.put("pageSize", dto.getPageSize().toString());
        }
        IPage<AreaListResultDTO> iPage = sysPlaceRegionMapper.apiListPage(new PageWrapper<AreaListResultDTO>(params).getPage(), dto);
        return new PageResult(iPage);
    }

    @Override
    public String getNameByCode(Integer code){
        return sysPlaceRegionMapper.getNameByCode(code);
    }

    @Override
    public AreaListResultDTO getOne(GetAreaDTO dto) {
        return sysPlaceRegionMapper.getOne(dto.getAreaCode());
    }
}
