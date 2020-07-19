package com.diandian.dubbo.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.SysOrgMapper;
import com.diandian.dubbo.business.mapper.SysOrgTypeMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.sys.OrgTypeDTO;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.model.sys.SysOrgTypeModel;
import com.diandian.dubbo.facade.service.sys.SysOrgTypeMenuService;
import com.diandian.dubbo.facade.service.sys.SysOrgTypeService;
import com.diandian.dubbo.facade.vo.StatisticsTypeOpenFeeVO;
import com.diandian.dubbo.facade.vo.StatisticsTypeOpenCntVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 系统机构服务
 *
 * @author x
 * @date 2018/09/20
 */
@Service("sysOrgTypeService")
public class SysOrgTypeServiceImpl implements SysOrgTypeService {

    @Autowired
    private SysOrgTypeMenuService sysOrgTypeMenuService;

    @Autowired
    private SysOrgTypeMapper sysOrgTypeMapper;

    @Autowired
    private SysOrgMapper sysOrgMapper;

    @Override
    public List<SysOrgTypeModel> list(OrgTypeDTO dto){
        QueryWrapper<SysOrgTypeModel> wrapper = new QueryWrapper<>();
        wrapper.eq(null != dto.getDelFlag(), "del_flag", dto.getDelFlag());
        if(null != dto.getLevel()){
            wrapper.eq(BizConstant.CompareType.EQ.value().equals(dto.getLevel().getCompare()), "level", dto.getLevel().getValue());
            wrapper.gt(BizConstant.CompareType.GT.value().equals(dto.getLevel().getCompare()), "level", dto.getLevel().getValue());
            wrapper.ge(BizConstant.CompareType.GE.value().equals(dto.getLevel().getCompare()), "level", dto.getLevel().getValue());
            wrapper.lt(BizConstant.CompareType.LT.value().equals(dto.getLevel().getCompare()), "level", dto.getLevel().getValue());
            wrapper.le(BizConstant.CompareType.LE.value().equals(dto.getLevel().getCompare()), "level", dto.getLevel().getValue());
        }
        return  sysOrgTypeMapper.selectList(wrapper);
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String typeName = (String) params.get("typeName");

        IPage<SysOrgTypeModel> page = sysOrgTypeMapper.selectPage(
                new PageWrapper<SysOrgTypeModel>(params).getPage(),
                new QueryWrapper<SysOrgTypeModel>().eq("del_flag", BizConstant.STATE_NORMAL)
                        .like(StringUtils.isNotBlank(typeName), "type_name", typeName)
        );
        return new PageResult(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertOrgType(SysOrgTypeModel orgType) {
        AssertUtil.notBlank(orgType.getTypeName(), "机构类型名称不能为空");
        AssertUtil.notBlank(orgType.getTypeCode(), "机构类型编码不能为空");
        QueryWrapper<SysOrgTypeModel> wrapper = new QueryWrapper<>();
        wrapper.eq("type_code", orgType.getTypeCode());
        AssertUtil.isTrue(sysOrgTypeMapper.selectCount(wrapper) == 0, "机构类型编码已存在");
        wrapper = new QueryWrapper<>();
        wrapper.eq("type_name", orgType.getTypeName());
        AssertUtil.isTrue(sysOrgTypeMapper.selectCount(wrapper) == 0, "机构类型名称已存在");
        sysOrgTypeMapper.insert(orgType);
        sysOrgTypeMenuService.saveOrgMenu(orgType.getId(), orgType.getMenuIdList());
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteOrgTypeById(Long id) {
        QueryWrapper<SysOrgModel> wrapper = new QueryWrapper<>();
        wrapper.eq("org_type_id", id);
        AssertUtil.isTrue(sysOrgMapper.selectCount(wrapper) == 0, "无法删除正在使用的机构类型");
        SysOrgTypeModel update = new SysOrgTypeModel();
        update.setId(id);
        update.setDelFlag(BizConstant.STATE_DISNORMAL);
        sysOrgTypeMapper.updateById(update);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateOrgType(SysOrgTypeModel orgType) {
        sysOrgTypeMapper.updateById(orgType);
        sysOrgTypeMenuService.saveOrgMenu(orgType.getId(), orgType.getMenuIdList());
        return Boolean.TRUE;
    }

    @Override
    public SysOrgTypeModel getById(Long id){
        return sysOrgTypeMapper.selectById(id);
    }

    @Override
    public List<StatisticsTypeOpenCntVO> getOrgTypeOpenOverview(Map<String, Object> params){
        return sysOrgTypeMapper.getOrgTypeOpenOverview(params);
    }

    @Override
    public List<StatisticsTypeOpenFeeVO> statisticsOrgTypeOpenFee(Map<String, Object> params){
        return sysOrgTypeMapper.statisticsOrgTypeOpenFee(params);
    }
}
