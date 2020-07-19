package com.diandian.dubbo.business.service.impl.transport;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.diandian.dubbo.business.mapper.transport.TransportFeeRuleAreaMapper;
import com.diandian.dubbo.business.mapper.transport.TransportFeeRuleMapper;
import com.diandian.dubbo.business.mapper.transport.TransportInfoMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.constant.TransportTypeConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.transport.TransportFeeRuleAreaModel;
import com.diandian.dubbo.facade.model.transport.TransportFeeRuleModel;
import com.diandian.dubbo.facade.model.transport.TransportInfoModel;
import com.diandian.dubbo.facade.service.transport.TransportInfoService;
import com.diandian.dubbo.facade.vo.TransportInfoVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 1运输方式信息 服务实现类
 * </p>
 *
 * @author zzj
 * @since 2019-02-28
 */
@Service("transportInfoService")
public class TransportInfoServiceImpl implements TransportInfoService {
    @Autowired
    private TransportInfoMapper transportInfoMapper;
    @Autowired
    private TransportFeeRuleMapper transportFeeRuleMapper;
    @Autowired
    private TransportFeeRuleAreaMapper transportFeeRuleAreaMapper;

    @Override
    public PageResult listPage(Map<String, Object> params){
        String repositoryId=(String)params.get("repositoryId");
        String keyword=(String)params.get("keyword");
        String shopId = null == params.get("shopId")?"":params.get("shopId").toString();
        IPage<TransportInfoModel> transportInfoModelIPage = transportInfoMapper.selectPage(
                new PageWrapper<TransportInfoModel>(params).getPage(),
                new LambdaQueryWrapper<TransportInfoModel>()
                        .eq(TransportInfoModel::getDelFlag, BizConstant.STATE_NORMAL)
                        .eq(StringUtils.isNotBlank(repositoryId),TransportInfoModel::getRepositoryId,repositoryId)
                        .eq(StringUtils.isNotBlank(shopId),TransportInfoModel::getShopId,shopId)
                        .like(StringUtils.isNotBlank(keyword),TransportInfoModel::getTransportName,keyword)
        );
        return new PageResult(transportInfoModelIPage);
    };
    @Override
    public TransportInfoModel getById(Long id){
        return transportInfoMapper.getById(id);
    };

    void updateRule(TransportInfoModel transportInfoModel){
        transportInfoModel.getTransportFeeRuleList().forEach(rule -> {
            rule.setTransportId(transportInfoModel.getId());
            transportFeeRuleMapper.insert(rule);
            List<TransportFeeRuleAreaModel> areaList=new ArrayList<>();
            Date date = new Date();
            rule.getTransportFeeRuleAreaList().forEach(regionCode -> {
                TransportFeeRuleAreaModel transportFeeRuleAreaModel = new TransportFeeRuleAreaModel();
                transportFeeRuleAreaModel.setId(IdWorker.getId());
                transportFeeRuleAreaModel.setTransportRuleId(rule.getId());
                transportFeeRuleAreaModel.setRegionCode(regionCode);
                transportFeeRuleAreaModel.setCreateTime(date);
                areaList.add(transportFeeRuleAreaModel);
//                transportFeeRuleAreaMapper.insert(transportFeeRuleAreaModel);
            });
            transportFeeRuleAreaMapper.insertBatch(areaList);

        });
    }
    void deleteRule(Long id){
        List<TransportFeeRuleModel> transportFeeRuleList = transportFeeRuleMapper.selectList(new QueryWrapper<TransportFeeRuleModel>().eq("transport_id", id));
        transportFeeRuleMapper.delete(new QueryWrapper<TransportFeeRuleModel>().eq("transport_id",id));
        if(transportFeeRuleList!=null){
            transportFeeRuleList.forEach(rule->{
                transportFeeRuleAreaMapper.delete(new QueryWrapper<TransportFeeRuleAreaModel>().eq("transport_rule_id",rule.getId()));
            });
        }
    }
    @Override
    public void updateTransportInfo(TransportInfoModel transportInfoModel){
        transportInfoMapper.updateById(transportInfoModel);
        Integer transportType = transportInfoModel.getTransportType();
        if(transportType== TransportTypeConstant.EXPRESS.getValue()) {
            Long id = transportInfoModel.getId();
            this.deleteRule(id);
            this.updateRule(transportInfoModel);
        }
    };

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTransportInfo(TransportInfoModel transportInfoModel){
        transportInfoMapper.insert(transportInfoModel);
        Integer transportType = transportInfoModel.getTransportType();
        if(transportType== TransportTypeConstant.EXPRESS.getValue()) {
            this.updateRule(transportInfoModel);
        }
    };
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTransportInfo(Long id){
        transportInfoMapper.deleteById(id);
        this.deleteRule(id);
    };

    @Override
    public List<TransportInfoModel> listDetail(List<Long> transportIds) {
        return transportInfoMapper.listDetail(transportIds);
    }

    @Override
    public List<TransportInfoModel> listByType(Integer type) {
        QueryWrapper<TransportInfoModel> qw = new QueryWrapper<>();
        qw.eq("transport_type",type);
        qw.eq("del_flag",0);
        qw.select("id");
        return transportInfoMapper.selectList(qw);
    }

    @Override
    public Integer checkRuleIncludeRegionCode(Long transportRuleId, String regionCode){
        if(null == transportRuleId || StringUtils.isBlank(regionCode)){
            return null;
        }
        return transportFeeRuleAreaMapper.count(transportRuleId, regionCode);
    }

    @Override
    public List<TransportInfoVO> getTransportVOList(String idsStr, List<Integer> transportTypeIds) {
        return transportInfoMapper.getTransportVOList(idsStr, transportTypeIds);
    }
}
