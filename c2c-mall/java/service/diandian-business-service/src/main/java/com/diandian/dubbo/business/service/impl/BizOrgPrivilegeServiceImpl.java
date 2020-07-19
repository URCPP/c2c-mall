package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.diandian.dubbo.business.mapper.BizOpenDetailMapper;
import com.diandian.dubbo.business.mapper.BizOrgPrivilegeMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.biz.OrgPrivilegeDTO;
import com.diandian.dubbo.facade.dto.biz.OrgPrivilegeQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizOpenDetailModel;
import com.diandian.dubbo.facade.model.biz.BizOrgPrivilegeModel;
import com.diandian.dubbo.facade.service.biz.BizOrgPrivilegeService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import com.diandian.dubbo.facade.vo.OrgPrivilegeListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/2/21 19:13
 */
@Service("bizOrgPrivilegeService")
@Slf4j
public class BizOrgPrivilegeServiceImpl implements BizOrgPrivilegeService {

    @Autowired
    private BizOrgPrivilegeMapper bizOrgPrivilegeMapper;

    @Autowired
    private BizOpenDetailMapper bizOpenDetailMapper;

    @Autowired
    private NoGenerator noGenerator;

    @Override
    public BizOrgPrivilegeModel getOne(OrgPrivilegeQueryDTO dto) {
        QueryWrapper<BizOrgPrivilegeModel> wrapper = new QueryWrapper<>();
        wrapper.eq(null != dto.getOrgId() && dto.getOrgId() > 0, "org_id", dto.getOrgId());
        wrapper.eq(null != dto.getRewardOrgTypeId(), "reward_org_type_id", dto.getRewardOrgTypeId());
        wrapper.eq(null != dto.getRewardSoftwareTypeId(), "reward_software_type_id", dto.getRewardSoftwareTypeId());
        return bizOrgPrivilegeMapper.selectOne(wrapper);
    }

    @Override
    public boolean insert(BizOrgPrivilegeModel orgPrivilege) {
        Integer result = bizOrgPrivilegeMapper.insert(orgPrivilege);
        return null != result && result >= 1;
    }

    @Override
    public boolean updateById(BizOrgPrivilegeModel orgPrivilege) {
        Integer result = bizOrgPrivilegeMapper.updateById(orgPrivilege);
        return null != result && result >= 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrgStrategy(Long orgId, List<OrgPrivilegeDTO> list){
        QueryWrapper<BizOrgPrivilegeModel> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id", orgId);
        List<OrgPrivilegeDTO> oldList = bizOrgPrivilegeMapper.queryOrgAllTypePrivilegeList(orgId);
        List<OrgPrivilegeDTO> newTempList = new ArrayList<>(list);
        //新的和旧的差集 = 要新增的list
        newTempList.removeAll(oldList);
        //新的和旧的交集 = 要修改的list
        list.retainAll(oldList);

        Iterator<OrgPrivilegeDTO> addList = newTempList.iterator();
        while(addList.hasNext()){
            OrgPrivilegeDTO dto = addList.next();
            BizOrgPrivilegeModel orgPrivilege = new BizOrgPrivilegeModel();
            BeanUtil.copyProperties(dto, orgPrivilege);
            orgPrivilege.setOrgId(orgId);
            QueryWrapper<BizOrgPrivilegeModel> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("org_id", orgId);
            queryWrapper.eq("reward_type_id", dto.getRewardTypeId());
            queryWrapper.eq(null != dto.getRewardOrgTypeId() && dto.getRewardOrgTypeId() > 0, "reward_org_type_id", dto.getRewardOrgTypeId());
            queryWrapper.eq(null != dto.getRewardSoftwareTypeId() && dto.getRewardSoftwareTypeId() > 0, "reward_software_type_id", dto.getRewardSoftwareTypeId());
            BizOrgPrivilegeModel old = bizOrgPrivilegeMapper.selectOne(queryWrapper);
            if(null == old){
                bizOrgPrivilegeMapper.insert(orgPrivilege);
                BizOpenDetailModel openDetail = new BizOpenDetailModel();
                openDetail.setOrgId(orgId);
                openDetail.setBusType(BizConstant.OpenBusType.ADMIN_EDIT.value());
                openDetail.setOpenType(dto.getRewardTypeId());
                openDetail.setTradeStart(0);
                openDetail.setTradeEnd(dto.getRewardValue().intValue());
                openDetail.setTradeNum(dto.getRewardValue().intValue());
                openDetail.setTradeNo(noGenerator.getRewardTradeNo());
                openDetail.setTradeType(BizConstant.TradeType.INCOME.value());
                openDetail.setTypeId(BizConstant.ObjectType.ORG.value().equals(dto.getRewardTypeId()) ? dto.getRewardOrgTypeId() : dto.getRewardSoftwareTypeId());
                bizOpenDetailMapper.insert(openDetail);
            }
        }
        Iterator<OrgPrivilegeDTO> editList = list.iterator();
        while(editList.hasNext()){
            OrgPrivilegeDTO dto = editList.next();
            BizOrgPrivilegeModel update = new BizOrgPrivilegeModel();
            update.setRewardValue(dto.getRewardValue());
            QueryWrapper<BizOrgPrivilegeModel> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("org_id", orgId);
            queryWrapper.eq("reward_type_id", dto.getRewardTypeId());
            queryWrapper.eq(null != dto.getRewardOrgTypeId() && dto.getRewardOrgTypeId() > 0, "reward_org_type_id", dto.getRewardOrgTypeId());
            queryWrapper.eq(null != dto.getRewardSoftwareTypeId() && dto.getRewardSoftwareTypeId() > 0, "reward_software_type_id", dto.getRewardSoftwareTypeId());
            BizOrgPrivilegeModel old = bizOrgPrivilegeMapper.selectOne(queryWrapper);
            if(null == old || dto.getRewardValue().compareTo(old.getRewardValue()) == 0){
                continue;
            }
            bizOrgPrivilegeMapper.update(update, queryWrapper);
            BizOpenDetailModel openDetail = new BizOpenDetailModel();
            openDetail.setOrgId(orgId);
            openDetail.setBusType(BizConstant.OpenBusType.ADMIN_EDIT.value());
            openDetail.setOpenType(dto.getRewardTypeId());
            openDetail.setTradeStart(old.getRewardValue().intValue());
            openDetail.setTradeEnd(dto.getRewardValue().intValue());
            openDetail.setTradeNo(noGenerator.getRewardTradeNo());
            if(old.getRewardValue().compareTo(dto.getRewardValue()) == 1){
                openDetail.setTradeNum(old.getRewardValue().subtract(dto.getRewardValue()).intValue());
                openDetail.setTradeType(BizConstant.TradeType.EXPENDITURE.value());
            }else {
                openDetail.setTradeNum(dto.getRewardValue().subtract(old.getRewardValue()).intValue());
                openDetail.setTradeType(BizConstant.TradeType.INCOME.value());
            }
            openDetail.setTypeId(BizConstant.ObjectType.ORG.value().equals(dto.getRewardTypeId()) ? dto.getRewardOrgTypeId() : dto.getRewardSoftwareTypeId());
            bizOpenDetailMapper.insert(openDetail);
        }

    }

    @Override
    public List<OrgPrivilegeListVO> queryOrgPrivilegeListVO(OrgPrivilegeQueryDTO dto){
        return bizOrgPrivilegeMapper.queryOrgPrivilegeListVO(dto);
    }

    @Override
    public List<OrgPrivilegeListVO> queryOrgTypePrivilege(Long orgId, Integer level) {
        return bizOrgPrivilegeMapper.queryOrgTypePrivilege(orgId, level);
    }

    @Override
    public List<OrgPrivilegeListVO> querySoftwareTypePrivilege(Long orgId) {
        return bizOrgPrivilegeMapper.querySoftwareTypePrivilege(orgId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recycleOrgPrivilege(Long orgId, OrgPrivilegeDTO dto) {
        AssertUtil.isTrue(dto.getRewardValue().compareTo(BigDecimal.ZERO) == 1, "回收数量必须大于零");
        QueryWrapper<BizOrgPrivilegeModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", orgId);
        queryWrapper.eq("reward_type_id", dto.getRewardTypeId());
        queryWrapper.eq(null != dto.getRewardOrgTypeId() && dto.getRewardOrgTypeId() > 0, "reward_org_type_id", dto.getRewardOrgTypeId());
        queryWrapper.eq(null != dto.getRewardSoftwareTypeId() && dto.getRewardSoftwareTypeId() > 0, "reward_software_type_id", dto.getRewardSoftwareTypeId());
        BizOrgPrivilegeModel old = bizOrgPrivilegeMapper.selectOne(queryWrapper);
        AssertUtil.notNull(old, "机构权益信息不存在");
        AssertUtil.isTrue(dto.getRewardValue().compareTo(old.getRewardValue()) <= 0, "回收数量超过现有数量");
        BizOrgPrivilegeModel update = new BizOrgPrivilegeModel();
        update.setId(old.getId());
        update.setRewardValue(old.getRewardValue().subtract(dto.getRewardValue()));
        bizOrgPrivilegeMapper.updateById(update);

        BizOpenDetailModel openDetail = new BizOpenDetailModel();
        openDetail.setOrgId(orgId);
        openDetail.setBusType(BizConstant.OpenBusType.ADMIN_EDIT.value());
        openDetail.setOpenType(dto.getRewardTypeId());
        openDetail.setTradeStart(old.getRewardValue().intValue());
        openDetail.setTradeEnd(old.getRewardValue().subtract(dto.getRewardValue()).intValue());
        openDetail.setTradeNum(dto.getRewardValue().intValue());
        openDetail.setTradeNo(noGenerator.getRewardTradeNo());
        openDetail.setTradeType(BizConstant.TradeType.EXPENDITURE.value());
        openDetail.setTypeId(BizConstant.ObjectType.ORG.value().equals(dto.getRewardTypeId()) ? dto.getRewardOrgTypeId() : dto.getRewardSoftwareTypeId());
        bizOpenDetailMapper.insert(openDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void giftOrgPrivilege(Long orgId, OrgPrivilegeDTO dto) {
        AssertUtil.isTrue(null != dto.getRewardValue() && dto.getRewardValue().compareTo(BigDecimal.ZERO) > 0, "赠送数量不能少于1");
        QueryWrapper<BizOrgPrivilegeModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("org_id", orgId);
        queryWrapper.eq("reward_type_id", dto.getRewardTypeId());
        queryWrapper.eq(null != dto.getRewardOrgTypeId() && dto.getRewardOrgTypeId() > 0, "reward_org_type_id", dto.getRewardOrgTypeId());
        queryWrapper.eq(null != dto.getRewardSoftwareTypeId() && dto.getRewardSoftwareTypeId() > 0, "reward_software_type_id", dto.getRewardSoftwareTypeId());
        BizOrgPrivilegeModel old = bizOrgPrivilegeMapper.selectOne(queryWrapper);
        BizOpenDetailModel openDetail = new BizOpenDetailModel();
        openDetail.setBusType(BizConstant.OpenBusType.ADMIN_EDIT.value());
        openDetail.setOpenType(dto.getRewardTypeId());
        openDetail.setOrgId(orgId);
        if(null != old){
            BizOrgPrivilegeModel update = new BizOrgPrivilegeModel();
            update.setId(old.getId());
            update.setRewardValue(old.getRewardValue().add(dto.getRewardValue()));
            bizOrgPrivilegeMapper.updateById(update);
            openDetail.setTradeStart(old.getRewardValue().intValue());
            openDetail.setTradeEnd(old.getRewardValue().add(dto.getRewardValue()).intValue());
        }else {
            openDetail.setTradeStart(0);
            openDetail.setTradeEnd(dto.getRewardValue().intValue());
            BizOrgPrivilegeModel insert = new BizOrgPrivilegeModel();
            insert.setOrgId(orgId);
            BeanUtil.copyProperties(dto, insert);
            bizOrgPrivilegeMapper.insert(insert);
        }
        openDetail.setTradeNum(dto.getRewardValue().intValue());
        openDetail.setTradeNo(noGenerator.getRewardTradeNo());
        openDetail.setTradeType(BizConstant.TradeType.INCOME.value());
        openDetail.setTypeId(BizConstant.ObjectType.ORG.value().equals(dto.getRewardTypeId()) ? dto.getRewardOrgTypeId() : dto.getRewardSoftwareTypeId());
        bizOpenDetailMapper.insert(openDetail);
    }
}
