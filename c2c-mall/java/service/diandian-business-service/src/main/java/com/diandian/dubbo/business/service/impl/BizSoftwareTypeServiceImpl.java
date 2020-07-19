package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.business.mapper.BizSoftwareTypeMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeModel;
import com.diandian.dubbo.facade.service.biz.BizSoftwareTypeService;
import com.diandian.dubbo.facade.vo.StatisticsTypeOpenCntVO;
import com.diandian.dubbo.facade.vo.StatisticsTypeOpenFeeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 软件类型
 *
 * @author wubc
 * @date 2019/02/18
 */
@Service("bizSoftwareTypeService")
@Slf4j
public class BizSoftwareTypeServiceImpl implements BizSoftwareTypeService {

    @Autowired
    private BizSoftwareTypeMapper bizSoftwareTypeMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String typeName = null == params.get("typeName")?"":params.get("typeName").toString();
        IPage<BizSoftwareTypeModel> page = bizSoftwareTypeMapper.selectPage(
                new PageWrapper<BizSoftwareTypeModel>(params).getPage(),
                new QueryWrapper<BizSoftwareTypeModel>().orderByAsc("create_time")
                        .eq("del_flag",BizConstant.STATE_NORMAL)
                        .like(StrUtil.isNotBlank(typeName),"type_name",typeName)
        );
        return new PageResult(page);
    }

    @Override
    public List<BizSoftwareTypeModel> listSoftType() {
        LambdaQueryWrapper<BizSoftwareTypeModel> qw = new LambdaQueryWrapper<>();
        qw.eq(BizSoftwareTypeModel::getDelFlag, BizConstant.STATE_NORMAL);
        return bizSoftwareTypeMapper.selectList(qw);
    }

    @Override
    public BizSoftwareTypeModel getSoftTypeById(Long softTypeId) {
        return bizSoftwareTypeMapper.selectById(softTypeId);
    }

    @Override
    public void updateById(BizSoftwareTypeModel bizSoftwareTypeModel) {
        bizSoftwareTypeMapper.updateById(bizSoftwareTypeModel);
    }

    @Override
    public void save(BizSoftwareTypeModel bizSoftwareTypeModel) {
        bizSoftwareTypeMapper.insert(bizSoftwareTypeModel);
    }

    @Override
    public void removeById(Long id) {
        bizSoftwareTypeMapper.deleteById(id);
    }

    @Override
    public List<StatisticsTypeOpenCntVO> getSoftwareOpenOverview(Map<String, Object> params){
        return bizSoftwareTypeMapper.getSoftwareOpenOverview(params);
    }

    @Override
    public List<StatisticsTypeOpenFeeVO> statisticsSoftwareTypeOpenFee(Map<String, Object> params){
        return bizSoftwareTypeMapper.statisticsSoftwareTypeOpenFee(params);
    }

    @Override
    public List<BizSoftwareTypeModel> getOrderBySoftware() {
        return bizSoftwareTypeMapper.selectList(
                new QueryWrapper<BizSoftwareTypeModel>()
                        .eq("del_flag",BizConstant.STATE_NORMAL)
                        .orderByAsc("type_code")
        );
    }
}
