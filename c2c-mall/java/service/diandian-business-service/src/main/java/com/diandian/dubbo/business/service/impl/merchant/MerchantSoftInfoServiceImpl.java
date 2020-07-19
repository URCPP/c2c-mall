package com.diandian.dubbo.business.service.impl.merchant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.BizSoftwareTypeMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantInfoMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantSoftHistoryLogMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantSoftInfoMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.merchant.MerchantSoftInfoDTO;
import com.diandian.dubbo.facade.model.biz.BizSoftwareTypeModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantSoftHistoryLogModel;
import com.diandian.dubbo.facade.model.merchant.MerchantSoftInfoModel;
import com.diandian.dubbo.facade.service.merchant.MerchantSoftInfoService;
import com.diandian.dubbo.facade.vo.merchant.MerchantSoftInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 商户软件信息表
 *
 * @author wbc
 * @date 2019/02/19
 */
@Service("merchantSoftInfoService")
@Slf4j
public class MerchantSoftInfoServiceImpl implements MerchantSoftInfoService {

    @Autowired
    private MerchantSoftInfoMapper merchantSoftInfoMapper;

    @Autowired
    private MerchantSoftHistoryLogMapper merchantSoftHistoryLogMapper;

    @Autowired
    private BizSoftwareTypeMapper bizSoftwareTypeMapper;

    @Autowired
    private MerchantInfoMapper merchantInfoMapper;


    @Override
    public MerchantSoftInfoModel getSoftAcc(Long merchantId, Long softId) {
        QueryWrapper<MerchantSoftInfoModel> qw = new QueryWrapper<>();
        qw.eq("merchant_id", merchantId);
        qw.eq("soft_type_id", softId);
        return merchantSoftInfoMapper.selectOne(qw);
    }

    @Override
    public boolean saveSoftAcc(MerchantSoftInfoModel mSoftAcc) {
        merchantSoftInfoMapper.insert(mSoftAcc);
        return true;
    }

    @Override
    public boolean updateSoftAccById(MerchantSoftInfoModel softAccCur) {
        merchantSoftInfoMapper.updateById(softAccCur);
        return true;
    }


    @Override
    public List<MerchantSoftInfoVO> listByMerchantId(Long merchantId) {
        return merchantSoftInfoMapper.listByMerchantId(merchantId);
    }

    @Override
    public void batchSaveMerchantSoftInfo(Long merchantId, List<MerchantSoftInfoDTO> list){
        QueryWrapper<MerchantSoftInfoModel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", merchantId);
        List<MerchantSoftInfoDTO> oldList = merchantSoftInfoMapper.queryMerchantSoftInfo(merchantId);
        List<MerchantSoftInfoDTO> newTempList = new ArrayList<>(list);
        //新的和旧的差集 = 要新增的list
        newTempList.removeAll(oldList);
        //新的和旧的交集 = 要修改的list
        list.retainAll(oldList);
        Iterator<MerchantSoftInfoDTO> addList = newTempList.iterator();
        while(addList.hasNext()){
            MerchantSoftInfoDTO dto = addList.next();
            QueryWrapper<MerchantSoftInfoModel> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("merchant_id", merchantId);
            queryWrapper.eq("soft_type_id", dto.getSoftTypeId());
            MerchantSoftInfoModel old = merchantSoftInfoMapper.selectOne(queryWrapper);
            if(null == old){
                MerchantSoftInfoModel merchantSoftInfo = new MerchantSoftInfoModel();
                merchantSoftInfo.setOpenNum(0);
                merchantSoftInfo.setMerchantId(merchantId);
                merchantSoftInfo.setSoftTypeId(dto.getSoftTypeId());
                merchantSoftInfo.setAvailableOpenNum(dto.getAvailableOpenNum());
                merchantSoftInfoMapper.insert(merchantSoftInfo);

                if(dto.getAvailableOpenNum() == 0){
                    continue;
                }
                MerchantSoftHistoryLogModel historyLog = new MerchantSoftHistoryLogModel();
                historyLog.setSoftTypeId(dto.getSoftTypeId());
                historyLog.setPreNum(0);
                historyLog.setPostNum(dto.getAvailableOpenNum());
                historyLog.setNum(dto.getAvailableOpenNum());
                historyLog.setBusinessType(BizConstant.OpenBusType.ADMIN_EDIT.value());
                historyLog.setMerchantId(merchantId);
                merchantSoftHistoryLogMapper.insert(historyLog);
            }
        }
        Iterator<MerchantSoftInfoDTO> editList = list.iterator();
        while(editList.hasNext()){
            MerchantSoftInfoDTO dto = editList.next();
            MerchantSoftInfoModel update = new MerchantSoftInfoModel();
            update.setAvailableOpenNum(dto.getAvailableOpenNum());
            QueryWrapper<MerchantSoftInfoModel> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("merchant_id", merchantId);
            queryWrapper.eq("soft_type_id", dto.getSoftTypeId());
            MerchantSoftInfoModel old = merchantSoftInfoMapper.selectOne(queryWrapper);
            if(null == old || dto.getAvailableOpenNum().compareTo(old.getAvailableOpenNum()) == 0){
                continue;
            }
            merchantSoftInfoMapper.update(update, queryWrapper);

            MerchantSoftHistoryLogModel historyLog = new MerchantSoftHistoryLogModel();
            historyLog.setSoftTypeId(dto.getSoftTypeId());
            historyLog.setPreNum(old.getAvailableOpenNum());
            historyLog.setPostNum(dto.getAvailableOpenNum());
            if(old.getAvailableOpenNum().compareTo(dto.getAvailableOpenNum()) == 1){
                historyLog.setNum(old.getAvailableOpenNum() - dto.getAvailableOpenNum());
            }else {
                historyLog.setNum(dto.getAvailableOpenNum() - old.getAvailableOpenNum());
            }
            historyLog.setBusinessType(BizConstant.OpenBusType.ADMIN_EDIT.value());
            historyLog.setMerchantId(merchantId);
            merchantSoftHistoryLogMapper.insert(historyLog);
        }
    }

    @Override
    public MerchantSoftInfoModel getSoftTypeByIdAndMerchantId(Long softTypeId, Long merchantId) {
        QueryWrapper<MerchantSoftInfoModel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", merchantId)
                .eq("soft_type_id", softTypeId);
        return merchantSoftInfoMapper.selectOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recycleSoftStrategy(Long mchId, MerchantSoftInfoDTO dto) {
        AssertUtil.isTrue(dto.getAvailableOpenNum() > 0, "回收数量必须大于零");
        QueryWrapper<MerchantSoftInfoModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_id", mchId);
        queryWrapper.eq("soft_type_id", dto.getSoftTypeId());
        MerchantSoftInfoModel old = merchantSoftInfoMapper.selectOne(queryWrapper);
        AssertUtil.notNull(old, "软件权益信息不存在");
        AssertUtil.isTrue(dto.getAvailableOpenNum() <= old.getAvailableOpenNum(), "回收数量超过现有数量");

        MerchantSoftInfoModel update = new MerchantSoftInfoModel();
        update.setId(old.getId());
        update.setAvailableOpenNum(old.getAvailableOpenNum() - dto.getAvailableOpenNum());
        merchantSoftInfoMapper.updateById(update);

        MerchantSoftHistoryLogModel historyLog = new MerchantSoftHistoryLogModel();
        historyLog.setSoftTypeId(dto.getSoftTypeId());
        BizSoftwareTypeModel softwareType = bizSoftwareTypeMapper.selectById(dto.getSoftTypeId());
        AssertUtil.notNull(softwareType, "软件信息不存在");
        AssertUtil.isTrue(BizConstant.STATE_NORMAL.equals(softwareType.getDelFlag()), "软件信息不存在");
        historyLog.setSoftTypeName(softwareType.getTypeName());
        historyLog.setPreNum(old.getAvailableOpenNum());
        historyLog.setNum(dto.getAvailableOpenNum());
        historyLog.setPostNum(old.getAvailableOpenNum() - dto.getAvailableOpenNum());
        historyLog.setBusinessType(BizConstant.OpenBusType.ADMIN_EDIT.value());
        historyLog.setMerchantId(mchId);
        MerchantInfoModel merchantInfo = merchantInfoMapper.selectById(mchId);
        AssertUtil.notNull(merchantInfo, "商户信息不存在");
        historyLog.setMerchantName(merchantInfo.getName());
        merchantSoftHistoryLogMapper.insert(historyLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void giftSoftStrategy(Long mchId, MerchantSoftInfoDTO dto) {
        AssertUtil.isTrue(null != dto.getAvailableOpenNum() && dto.getAvailableOpenNum() > 0, "赠送数量不能少于1");
        QueryWrapper<MerchantSoftInfoModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_id", mchId);
        queryWrapper.eq("soft_type_id", dto.getSoftTypeId());
        MerchantSoftInfoModel old = merchantSoftInfoMapper.selectOne(queryWrapper);
        MerchantSoftHistoryLogModel historyLog = new MerchantSoftHistoryLogModel();
        if(null != old){
            MerchantSoftInfoModel update = new MerchantSoftInfoModel();
            update.setId(old.getId());
            update.setAvailableOpenNum(old.getAvailableOpenNum() + dto.getAvailableOpenNum());
            merchantSoftInfoMapper.updateById(update);
            historyLog.setPreNum(old.getAvailableOpenNum());
            historyLog.setPostNum(old.getAvailableOpenNum() + dto.getAvailableOpenNum());
        }else {
            MerchantSoftInfoModel insert = new MerchantSoftInfoModel();
            insert.setSoftTypeId(dto.getSoftTypeId());
            insert.setMerchantId(mchId);
            insert.setAvailableOpenNum(dto.getAvailableOpenNum());
            insert.setOpenNum(0);
            merchantSoftInfoMapper.insert(insert);
            historyLog.setPreNum(0);
            historyLog.setPostNum(dto.getAvailableOpenNum());
        }
        historyLog.setSoftTypeId(dto.getSoftTypeId());
        BizSoftwareTypeModel softwareType = bizSoftwareTypeMapper.selectById(dto.getSoftTypeId());
        AssertUtil.notNull(softwareType, "软件信息不存在");
        AssertUtil.isTrue(BizConstant.STATE_NORMAL.equals(softwareType.getDelFlag()), "软件信息不存在");
        historyLog.setSoftTypeName(softwareType.getTypeName());
        historyLog.setNum(dto.getAvailableOpenNum());
        historyLog.setBusinessType(BizConstant.OpenBusType.ADMIN_EDIT.value());
        historyLog.setMerchantId(mchId);
        MerchantInfoModel merchantInfo = merchantInfoMapper.selectById(mchId);
        AssertUtil.notNull(merchantInfo, "商户信息不存在");
        historyLog.setMerchantName(merchantInfo.getName());
        merchantSoftHistoryLogMapper.insert(historyLog);
    }
}
