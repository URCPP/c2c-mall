package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.BizOrgApplyMapper;
import com.diandian.dubbo.business.mapper.BizOrgInfoMapper;
import com.diandian.dubbo.business.mapper.SysOrgMapper;
import com.diandian.dubbo.business.mapper.merchant.MerchantInfoMapper;
import com.diandian.dubbo.business.service.OrgCommonService;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.common.util.TreeUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.sys.OrgFormDTO;
import com.diandian.dubbo.facade.dto.sys.OrgQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizAccountModel;
import com.diandian.dubbo.facade.model.biz.BizOrgApplyModel;
import com.diandian.dubbo.facade.model.biz.BizOrgInfoModel;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.model.sys.SysOrgTypeModel;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.service.sys.SysOrgService;
import com.diandian.dubbo.facade.vo.IntactTreeVO;
import com.diandian.dubbo.facade.vo.OrgDetailVO;
import com.diandian.dubbo.facade.vo.OrgListVO;
import com.diandian.dubbo.facade.vo.StatisticsMerchantSalesVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统机构服务
 *
 * @author x
 * @date 2018/09/20
 */
@Slf4j
@Service("sysOrgService")
public class SysOrgServiceImpl extends OrgCommonService implements SysOrgService {

    @Autowired
    private SysOrgMapper sysOrgMapper;

    @Autowired
    private BizOrgInfoMapper bizOrgInfoMapper;

    @Autowired
    private BizOrgApplyMapper bizOrgApplyMapper;

    @Autowired
    private MerchantInfoMapper merchantInfoMapper;

    @Autowired
    private OrderInfoService orderInfoService;

    @Override
    public List<OrgListVO> listTree(Map<String, Object> params) {
        List<OrgListVO> list = sysOrgMapper.queryOrgListVO(params);
        List<OrgListVO> resultList = new ArrayList<>();
        if(list.size() == 0){
            return resultList;
        }
        resultList.add(list.remove(0));
        return TreeUtil.buildDeptTree(resultList, list, false, list.size());
    }

    @Override
    public PageResult listPage(Map<String, Object> params){
        Page<OrgListVO> page = new PageWrapper<OrgListVO>(params).getPage();
        page = sysOrgMapper.listPage(page, params);
        return new PageResult(page);
    }

    @Override
    public OrgDetailVO getOrgDetailVO(Long id){
        OrgDetailVO detail = sysOrgMapper.getOrgDetailVO(id);
        if(null != detail.getRecommendId() && detail.getRecommendId() > 0){
            detail.setRecommendName(sysOrgMapper.getNameById(detail.getRecommendId()));
        }
        if(null != detail.getParentId() && detail.getParentId() > 0){
            detail.setParentName(sysOrgMapper.getNameById(detail.getParentId()));
        }
        return detail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertOrg(OrgFormDTO dto) {
        //添加机构
        AssertUtil.notBlank(dto.getOrgName(), "机构名称不能为空");
        AssertUtil.notBlank(dto.getContactName(), "联系人不能为空");
        AssertUtil.notBlank(dto.getPhone(), "手机号码不能为空");
        AssertUtil.isTrue(null != dto.getOpenType(), "开通类型不能为空");
        AssertUtil.isTrue(dto.getOpenType() == 0 || dto.getOpenType() == 1, "参数错误");
        AssertUtil.isTrue(null != dto.getOrgTypeId() && dto.getOrgTypeId() > 0, "机构类型不能为空");
        QueryWrapper<SysOrgTypeModel> orgTypeWrapper = new QueryWrapper<>();
        orgTypeWrapper.eq("id", dto.getOrgTypeId());
        AssertUtil.isTrue(sysOrgTypeMapper.selectCount(orgTypeWrapper) > 0, "机构类型不存在");
        QueryWrapper<SysOrgModel> orgWrapper = new QueryWrapper<>();
        orgWrapper.eq("org_name", dto.getOrgName());
        orgWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
        AssertUtil.isTrue(sysOrgMapper.selectCount(orgWrapper) == 0, "机构名称已存在");
        QueryWrapper<BizOrgApplyModel> orgApplyWrapper = new QueryWrapper<>();
        orgApplyWrapper.eq("org_name", dto.getOrgName());
        orgApplyWrapper.ne("audit_state", BizConstant.AuditState.CLOSE_APPLY.value());
        AssertUtil.isTrue(bizOrgApplyMapper.selectCount(orgApplyWrapper) == 0, "机构名称已存在");
        if(StringUtils.isNotBlank(dto.getBusinessLicenseCode())){
            QueryWrapper<BizOrgInfoModel> orgInfoWrapper = new QueryWrapper<>();
            orgInfoWrapper.eq("business_license_code", dto.getBusinessLicenseCode());
            orgInfoWrapper.eq("org_type_id", dto.getOrgTypeId());
            orgInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
            AssertUtil.isTrue(bizOrgInfoMapper.selectCount(orgInfoWrapper) == 0, "该营业执照已被使用");
            //判断申请列表中的营业执照是否存在
            orgApplyWrapper = new QueryWrapper<>();
            orgApplyWrapper.eq("business_license_code", dto.getBusinessLicenseCode());
            orgApplyWrapper.eq("org_type_id", dto.getOrgTypeId());
            orgApplyWrapper.ne("audit_state", BizConstant.AuditState.CLOSE_APPLY.value());
            AssertUtil.isTrue(bizOrgApplyMapper.selectCount(orgApplyWrapper) == 0, "该营业执照已被使用");
        }

        SysOrgModel org = new SysOrgModel();
        BeanUtil.copyProperties(dto, org);
        BizOrgInfoModel orgInfo = new BizOrgInfoModel();
        BeanUtil.copyProperties(dto, orgInfo);
        SysOrgModel parentOrg = null;
        if(null != dto.getParentId() && dto.getParentId() > 0){
            parentOrg = sysOrgMapper.selectNotDeleteOrgById(dto.getParentId());
            AssertUtil.notNull(parentOrg, "上级机构不存在");
            org.setTreeStr(StringUtils.isNotBlank(parentOrg.getTreeStr()) ? parentOrg.getTreeStr()+ "," + parentOrg.getId() : "" + parentOrg.getId());
            orgInfo.setParentId(parentOrg.getId());
        }
        SysOrgModel recommendOrg = null;
        if(null != dto.getRecommendId() && dto.getRecommendId() > 0 &&
                BizConstant.ApplyType.APPLY.value().equals(orgInfo.getOpenType())){
            recommendOrg = sysOrgMapper.selectNotDeleteOrgById(dto.getRecommendId());
            AssertUtil.notNull(recommendOrg, "推荐机构不存在");
            orgInfo.setRecommendId(recommendOrg.getId());
            //添加推荐机构奖励
            addRecommendOrgReward(recommendOrg, org);
        }
        orgInfo.setOrgCode(noGenerator.getOrgNo());
        //添加图片信息
        addImageInfo(dto, null, orgInfo, null);
        sysOrgMapper.insert(org);

        //添加机构详细信息
        orgInfo.setOrgId(org.getId());
        bizOrgInfoMapper.insert(orgInfo);

        //添加机构账户
        BizAccountModel account = new BizAccountModel();
        account.setOrgId(org.getId());
        account.setAccountNo(noGenerator.getOrgAccountNo());
        bizAccountMapper.insert(account);
        if(BizConstant.ApplyType.APPLY.value().equals(orgInfo.getOpenType())){
            //添加机构开通奖励
            addGiftReward(org);
        }
        //添加开通机构奖励
        if(null != parentOrg && null == recommendOrg &&
                BizConstant.ApplyType.APPLY.value().equals(orgInfo.getOpenType())){
            addOpenOrgReward(parentOrg, org);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateOrgById(OrgFormDTO dto) {
        SysOrgModel oldOrg = sysOrgMapper.selectNotDeleteOrgById(dto.getOrgId());
        AssertUtil.notNull(oldOrg, "机构不存在");
        AssertUtil.notBlank(dto.getOrgName(), "机构名称不能为空");
        AssertUtil.notBlank(dto.getContactName(), "联系人不能为空");
        AssertUtil.notBlank(dto.getPhone(), "手机号码不能为空");
        AssertUtil.isTrue(null != dto.getOrgTypeId() && dto.getOrgTypeId() > 0, "机构类型不能为空");
        QueryWrapper<SysOrgTypeModel> orgTypeWrapper = new QueryWrapper<>();
        orgTypeWrapper.eq("id", dto.getOrgTypeId());
        AssertUtil.isTrue(sysOrgTypeMapper.selectCount(orgTypeWrapper) > 0, "机构类型不存在");
        AssertUtil.isTrue(!dto.getOrgId().equals(dto.getParentId()), "上级机构不能为设置为自身");
        QueryWrapper<SysOrgModel> orgWrapper = new QueryWrapper<>();
        orgWrapper.ne("id", oldOrg.getId());
        orgWrapper.eq("org_name", dto.getOrgName());
        orgWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
        AssertUtil.isTrue(sysOrgMapper.selectCount(orgWrapper) == 0, "机构名称已存在");

        QueryWrapper<BizOrgInfoModel> orgInfoWrapper = new QueryWrapper<>();
        orgInfoWrapper.eq("org_id", oldOrg.getId());
        BizOrgInfoModel oldOrgInfo = bizOrgInfoMapper.selectOne(orgInfoWrapper);
        AssertUtil.notNull(oldOrgInfo, "机构信息不存在");
        QueryWrapper<BizOrgApplyModel> orgApplyWrapper = new QueryWrapper<>();
        orgApplyWrapper.ne("id", oldOrg.getApplyId());
        orgApplyWrapper.eq("org_name", dto.getOrgName());
        orgApplyWrapper.ne("audit_state", BizConstant.AuditState.CLOSE_APPLY.value());
        AssertUtil.isTrue(bizOrgApplyMapper.selectCount(orgApplyWrapper) == 0, "机构名称已存在");
        if(StringUtils.isNotBlank(dto.getBusinessLicenseCode())){
            orgInfoWrapper = new QueryWrapper<>();
            orgInfoWrapper.ne("id", oldOrgInfo.getId());
            orgInfoWrapper.eq("org_type_id", oldOrgInfo.getOrgTypeId());
            orgInfoWrapper.eq("business_license_code", dto.getBusinessLicenseCode());
            orgInfoWrapper.eq("del_flag", BizConstant.STATE_NORMAL);
            AssertUtil.isTrue(bizOrgInfoMapper.selectCount(orgInfoWrapper) == 0, "该营业执照已被使用");
            //判断申请列表中的营业执照是否存在
            orgApplyWrapper = new QueryWrapper<>();
            if(null != oldOrg.getApplyId() && oldOrg.getApplyId() > 0){
                orgApplyWrapper.ne("id", oldOrg.getApplyId());
            }
            orgApplyWrapper.eq("business_license_code", dto.getBusinessLicenseCode());
            orgApplyWrapper.eq("org_type_id", dto.getOrgTypeId());
            orgApplyWrapper.ne("audit_state", BizConstant.AuditState.CLOSE_APPLY.value());
            AssertUtil.isTrue(bizOrgApplyMapper.selectCount(orgApplyWrapper) == 0, "该营业执照已被使用");
        }

        //更新机构
        SysOrgModel updateOrg = new SysOrgModel();
        updateOrg.setOrgName(dto.getOrgName());
        updateOrg.setSort(dto.getSort());
        updateOrg.setState(dto.getState());
        updateOrg.setApproveFlag(dto.getApproveFlag());
        //更新机构信息
        BizOrgInfoModel updateOrgInfo = new BizOrgInfoModel();
        updateOrgInfo.setOrgName(dto.getOrgName());
        updateOrgInfo.setState(dto.getState());
        updateOrgInfo.setAccountName(dto.getAccountName());
        updateOrgInfo.setOpenBranchBank(dto.getOpenBranchBank());
        updateOrgInfo.setOpenBank(dto.getOpenBank());
        updateOrgInfo.setIdcard(dto.getIdcard());
        updateOrgInfo.setEmail(dto.getEmail());
        updateOrgInfo.setPhone(dto.getPhone());
        updateOrgInfo.setAddress(dto.getAddress());
        updateOrgInfo.setAreaName(dto.getAreaName());
        updateOrgInfo.setAreaCode(dto.getAreaCode());
        updateOrgInfo.setCityName(dto.getCityName());
        updateOrgInfo.setProvinceName(dto.getProvinceName());
        updateOrgInfo.setBankCardNo(dto.getBankCardNo());
        updateOrgInfo.setBusinessLicenseCode(dto.getBusinessLicenseCode());
        updateOrgInfo.setCityCode(dto.getCityCode());
        updateOrgInfo.setContactName(dto.getContactName());
        updateOrgInfo.setProvinceCode(dto.getProvinceCode());
        updateOrgInfo.setOpenType(dto.getOpenType());
        updateOrgInfo.setInfoIsPersonal(dto.getInfoIsPersonal());
        if(null != dto.getParentId() && dto.getParentId() > 0){
            SysOrgModel parentOrg = sysOrgMapper.selectNotDeleteOrgById(dto.getParentId());
            AssertUtil.notNull(parentOrg, "上级机构信息不存在");
            if(!dto.getParentId().equals(oldOrg.getParentId())){
                orgWrapper = new QueryWrapper<>();
                orgWrapper.eq("id", dto.getParentId());
                orgWrapper.like("tree_str", dto.getOrgId());
                AssertUtil.isTrue(sysOrgMapper.selectCount(orgWrapper) == 0, "上级机构不能设置成自己的子级");
            }
            updateOrg.setParentId(parentOrg.getId());
            updateOrg.setTreeStr(StringUtils.isNotBlank(parentOrg.getTreeStr()) ? parentOrg.getTreeStr()+ "," + parentOrg.getId() : "" + parentOrg.getId());
            updateOrgInfo.setParentId(parentOrg.getId());
        }
        updateOrg.setId(oldOrg.getId());
        sysOrgMapper.updateById(updateOrg);

        //更新机构信息
        updateOrgInfo.setId(oldOrgInfo.getId());
        addImageInfo(dto, null, updateOrgInfo, null);
        bizOrgInfoMapper.updateById(updateOrgInfo);
        return Boolean.TRUE;
    }

    @Override
    public SysOrgModel getById(Long id){
        return sysOrgMapper.selectNotDeleteOrgById(id);
    }

    @Override
    public Integer count(OrgQueryDTO dto){
        QueryWrapper<SysOrgModel> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(dto.getOrgName()), "org_name", dto.getOrgName());
        wrapper.eq(null != dto.getParentId(), "parent_id", dto.getParentId());
        wrapper.eq(null != dto.getId(), "id", dto.getId());
        wrapper.eq("del_flag", BizConstant.STATE_NORMAL);
        wrapper.like(StringUtils.isNotBlank(dto.getTreeStr()), "tree_str", dto.getTreeStr());
        return sysOrgMapper.selectCount(wrapper);
    }

    @Override
    public Integer unionCount(OrgQueryDTO dto){
        return sysOrgMapper.count(dto);
    }

    @Override
    public List<IntactTreeVO> getIntactTreeOrgPart(Map<String, Object> params){
        List<IntactTreeVO> list = sysOrgMapper.getIntactTreeOrgPart(params);
        if(list.isEmpty()){
            return list;
        }
        List<IntactTreeVO> resultList = new ArrayList<>();
        resultList.add(list.remove(0));
        return TreeUtil.buildIntactTree(resultList, list, false, list.size());
    }

    @Override
    public SysOrgModel getOne(OrgQueryDTO dto){
        QueryWrapper<SysOrgModel> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(dto.getOrgName()), "org_name", dto.getOrgName());
        wrapper.eq(null != dto.getApplyId(), "apply_id", dto.getApplyId());
        wrapper.eq("del_flag", BizConstant.STATE_NORMAL);
        return sysOrgMapper.selectOne(wrapper);
    }

    @Override
    public List<Map<String, Object>> getExportOrgList(Map<String, Object> params){
        return sysOrgMapper.getExportOrgList(params);
    }

    @Override
    public PageResult listMchSalesOverviewPage(Map<String, Object> params){
        Page<StatisticsMerchantSalesVO> page = new PageWrapper<StatisticsMerchantSalesVO>(params).getPage();
        IPage<StatisticsMerchantSalesVO> ipage = sysOrgMapper.listMchPageByOrgId(page, params);
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        List<StatisticsMerchantSalesVO> resList = orderInfoService.listMchSalesByMchIds(ipage.getRecords(), startTime, endTime);
        ipage.setRecords(resList);
        return new PageResult(ipage);
    }

    @Override
    public List<Long> getRecommendIdListByOrgId(Long orgId){
        return sysOrgMapper.getRecommendIdListByOrgId(orgId);
    }
}
