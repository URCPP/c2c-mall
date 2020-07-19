package com.diandian.admin.business.modules.biz.controller;

import cn.hutool.core.date.DateUtil;
import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.OrgAccountQueryDTO;
import com.diandian.dubbo.facade.dto.biz.OrgApplyQueryDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantInfoQueryDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantOpenApplyLogQueryDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantPayFeeRecordQueryDTO;
import com.diandian.dubbo.facade.dto.order.OrderInfoQueryDTO;
import com.diandian.dubbo.facade.dto.sys.OrgQueryDTO;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.sys.SysOrgTypeModel;
import com.diandian.dubbo.facade.service.biz.*;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.merchant.MerchantOpenApplyLogService;
import com.diandian.dubbo.facade.service.merchant.MerchantPayfeeRecordService;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.service.sys.SysOrgService;
import com.diandian.dubbo.facade.service.sys.SysOrgTypeService;
import com.diandian.dubbo.facade.vo.StatisticsByDayVO;
import com.diandian.dubbo.facade.vo.StatisticsSalesOverviewVO;
import com.diandian.dubbo.facade.vo.StatisticsTypeOpenCntVO;
import com.diandian.dubbo.facade.vo.StatisticsTypeOpenFeeVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计
 * @author cjunyuan
 * @date 2019/3/7 16:39
 */
@RequestMapping("/statistical")
@RestController
public class StatisticalController extends BaseController {

    @Autowired
    private SysOrgService sysOrgService;

    @Autowired
    private MerchantInfoService merchantInfoService;

    @Autowired
    private BizOrgApplyService bizOrgApplyService;

    @Autowired
    private MerchantOpenApplyLogService merchantOpenApplyLogService;

    @Autowired
    private MerchantPayfeeRecordService merchantPayfeeRecordService;

    @Autowired
    private BizAccountDetailService bizAccountDetailService;

    @Autowired
    private BizBonusDetailService bizBonusDetailService;

    @Autowired
    private BizCommissionDetailService bizCommissionDetailService;

    @Autowired
    private SysOrgTypeService sysOrgTypeService;

    @Autowired
    private BizSoftwareTypeService bizSoftwareTypeService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     *
     * 功能描述: 组织概况
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/3/7 16:43
     */
    @GetMapping("/organization/profile")
    public RespData organizationProfile(){
        Map<String, Integer> organizationProfile = new HashMap<>();
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        OrgQueryDTO orgQueryDTO = new OrgQueryDTO();
        MerchantInfoQueryDTO mchQueryDTO = new MerchantInfoQueryDTO();
        mchQueryDTO.setApproveFlag(MerchantConstant.MerchantApproveState.APPROVED.value());
        OrgApplyQueryDTO orgApplyQueryDTO = new OrgApplyQueryDTO();
        MerchantOpenApplyLogQueryDTO merchantOpenApplyLogQueryDTO = new MerchantOpenApplyLogQueryDTO();
        orgApplyQueryDTO.setAuditState(BizConstant.AuditState.AUDIT_WAIT.value());
        merchantOpenApplyLogQueryDTO.setAuditState(BizConstant.AuditState.AUDIT_WAIT.value());
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            orgQueryDTO.setTreeStr(orgId.toString());
            mchQueryDTO.setTreeStr(orgId.toString());
            orgApplyQueryDTO.setTreeStr(orgId.toString());
            merchantOpenApplyLogQueryDTO.setTreeStr(orgId.toString());
        }
        organizationProfile.put("orgCnt", sysOrgService.count(orgQueryDTO));
        orgQueryDTO.setOpenType(BizConstant.ApplyType.APPLY.value());
        organizationProfile.put("orgPayCnt", sysOrgService.unionCount(orgQueryDTO));
        orgQueryDTO.setOpenType(BizConstant.ApplyType.OPEN.value());
        organizationProfile.put("orgFreeCnt", sysOrgService.unionCount(orgQueryDTO));
        organizationProfile.put("mchCnt", merchantInfoService.count(mchQueryDTO));
        mchQueryDTO.setOpenType(BizConstant.ApplyType.APPLY.value());
        organizationProfile.put("mchPayCnt", merchantInfoService.count(mchQueryDTO));
        mchQueryDTO.setOpenType(BizConstant.ApplyType.OPEN.value());
        organizationProfile.put("mchFreeCnt", merchantInfoService.count(mchQueryDTO));
        organizationProfile.put("unReviewedOrgCnt", bizOrgApplyService.count(orgApplyQueryDTO));
        organizationProfile.put("unReviewedMchCnt", merchantOpenApplyLogService.count(merchantOpenApplyLogQueryDTO));
        return RespData.ok(organizationProfile);
    }

    @GetMapping("/apply/overview")
    public RespData applyOverview(){
        Map<String, Integer> applyOverview = new HashMap<>();
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        OrgApplyQueryDTO orgApplyQueryDTO = new OrgApplyQueryDTO();
        MerchantOpenApplyLogQueryDTO merchantOpenApplyLogQueryDTO = new MerchantOpenApplyLogQueryDTO();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            orgApplyQueryDTO.setTreeStr(orgId.toString());
            merchantOpenApplyLogQueryDTO.setTreeStr(orgId.toString());
        }
        String yesterdayStartTime = DateUtil.formatDateTime(DateUtil.beginOfDay(DateUtil.yesterday()));
        String yesterdayEndTime = DateUtil.formatDateTime(DateUtil.endOfDay(DateUtil.yesterday()));
        String todayStartTime = DateUtil.formatDateTime(DateUtil.beginOfDay(DateUtil.date()));
        String todayEndTime = DateUtil.formatDateTime(DateUtil.endOfDay(DateUtil.date()));
        orgApplyQueryDTO.setStartTime(yesterdayStartTime);
        orgApplyQueryDTO.setEndTime(yesterdayEndTime);
        merchantOpenApplyLogQueryDTO.setStartTime(yesterdayStartTime);
        merchantOpenApplyLogQueryDTO.setEndTime(yesterdayEndTime);
        orgApplyQueryDTO.setApplyType(BizConstant.ApplyType.APPLY.value());
        merchantOpenApplyLogQueryDTO.setApplyType(BizConstant.ApplyType.APPLY.value());
        applyOverview.put("yesterdayOrgApplyCnt", bizOrgApplyService.count(orgApplyQueryDTO));
        applyOverview.put("yesterdayMchApplyCnt", merchantOpenApplyLogService.count(merchantOpenApplyLogQueryDTO));
        orgApplyQueryDTO.setApplyType(BizConstant.ApplyType.OPEN.value());
        merchantOpenApplyLogQueryDTO.setApplyType(BizConstant.ApplyType.OPEN.value());
        applyOverview.put("yesterdayOrgOpenCnt", bizOrgApplyService.count(orgApplyQueryDTO));
        applyOverview.put("yesterdayMchOpenCnt", merchantOpenApplyLogService.count(merchantOpenApplyLogQueryDTO));

        orgApplyQueryDTO.setStartTime(todayStartTime);
        orgApplyQueryDTO.setEndTime(todayEndTime);
        merchantOpenApplyLogQueryDTO.setStartTime(todayStartTime);
        merchantOpenApplyLogQueryDTO.setEndTime(todayEndTime);
        applyOverview.put("todayOrgOpenCnt", bizOrgApplyService.count(orgApplyQueryDTO));
        applyOverview.put("todayMchOpenCnt", merchantOpenApplyLogService.count(merchantOpenApplyLogQueryDTO));
        orgApplyQueryDTO.setApplyType(BizConstant.ApplyType.APPLY.value());
        merchantOpenApplyLogQueryDTO.setApplyType(BizConstant.ApplyType.APPLY.value());
        applyOverview.put("todayOrgApplyCnt", bizOrgApplyService.count(orgApplyQueryDTO));
        applyOverview.put("todayMchApplyCnt", merchantOpenApplyLogService.count(merchantOpenApplyLogQueryDTO));
        return RespData.ok(applyOverview);
    }

    @GetMapping("/orgType/openCnt")
    public RespData orgTypeOpenOverview(){
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        List<StatisticsTypeOpenCntVO> total = sysOrgTypeService.getOrgTypeOpenOverview(params);
        String yesterdayStartTime = DateUtil.formatDateTime(DateUtil.beginOfDay(DateUtil.yesterday()));
        String yesterdayEndTime = DateUtil.formatDateTime(DateUtil.endOfDay(DateUtil.yesterday()));
        params.put("startTime", yesterdayStartTime);
        params.put("endTime", yesterdayEndTime);
        List<StatisticsTypeOpenCntVO> yesterday = sysOrgTypeService.getOrgTypeOpenOverview(params);
        String todayStartTime = DateUtil.formatDateTime(DateUtil.beginOfDay(DateUtil.date()));
        String todayEndTime = DateUtil.formatDateTime(DateUtil.endOfDay(DateUtil.date()));
        params.put("startTime", todayStartTime);
        params.put("endTime", todayEndTime);
        List<StatisticsTypeOpenCntVO> today = sysOrgTypeService.getOrgTypeOpenOverview(params);
        res.put("total", total);
        res.put("yesterday", yesterday);
        res.put("today", today);
        return RespData.ok(res);
    }

    @GetMapping("/softwareType/openCnt")
    public RespData softwareTypeOpenOverview(){
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        List<StatisticsTypeOpenCntVO> total = bizSoftwareTypeService.getSoftwareOpenOverview(params);
        String yesterdayStartTime = DateUtil.formatDateTime(DateUtil.beginOfDay(DateUtil.yesterday()));
        String yesterdayEndTime = DateUtil.formatDateTime(DateUtil.endOfDay(DateUtil.yesterday()));
        params.put("startTime", yesterdayStartTime);
        params.put("endTime", yesterdayEndTime);
        List<StatisticsTypeOpenCntVO> yesterday = bizSoftwareTypeService.getSoftwareOpenOverview(params);
        String todayStartTime = DateUtil.formatDateTime(DateUtil.beginOfDay(DateUtil.date()));
        String todayEndTime = DateUtil.formatDateTime(DateUtil.endOfDay(DateUtil.date()));
        params.put("startTime", todayStartTime);
        params.put("endTime", todayEndTime);
        List<StatisticsTypeOpenCntVO> today = bizSoftwareTypeService.getSoftwareOpenOverview(params);
        res.put("total", total);
        res.put("yesterday", yesterday);
        res.put("today", today);
        return RespData.ok(res);
    }

    @GetMapping("/orgType/openFee")
    public RespData getOrgTypeOpenFee(@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime){
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(startTime)){
            params.put("startTime", startTime);
        }
        if(StringUtils.isNotBlank(endTime)){
            params.put("endTime", endTime);
        }
        List<StatisticsTypeOpenFeeVO> list = sysOrgTypeService.statisticsOrgTypeOpenFee(params);
        return RespData.ok(list);
    }

    @GetMapping("/softwareType/openFee")
    public RespData getSoftwareTypeOpenFee(@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime){
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(startTime)){
            params.put("startTime", startTime);
        }
        if(StringUtils.isNotBlank(endTime)){
            params.put("endTime", endTime);
        }
        List<StatisticsTypeOpenFeeVO> list = bizSoftwareTypeService.statisticsSoftwareTypeOpenFee(params);
        return RespData.ok(list);
    }

    @GetMapping("/business/overview")
    public RespData businessOverview(String startTime, String endTime){
        Map<String, Object> businessOverview = new HashMap<>();
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        MerchantPayFeeRecordQueryDTO queryDTO = new MerchantPayFeeRecordQueryDTO();
        MerchantInfoQueryDTO merchantInfoQueryDTO = new MerchantInfoQueryDTO();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            merchantInfoQueryDTO.setTreeStr(orgId.toString());
            List<Long> mchIdList = merchantInfoService.queryMerchantIdList(merchantInfoQueryDTO);
            queryDTO.setMerchantIdList(mchIdList);
        }
        queryDTO.setPayType(1);
        queryDTO.setStartTime(startTime);
        queryDTO.setEndTime(endTime);
        List<StatisticsByDayVO> renewalFeeList = merchantPayfeeRecordService.statisticsByDayVO(queryDTO);
        businessOverview.put("renewalFeeList", renewalFeeList);
        queryDTO.setPayType(0);
        List<StatisticsByDayVO> rechargeList = merchantPayfeeRecordService.statisticsByDayVO(queryDTO);
        businessOverview.put("rechargeList", rechargeList);
        if(renewalFeeList.size() == 2 || rechargeList.size() == 2){
            Map<String, Object> totalFee = merchantPayfeeRecordService.statisticsTotalFee();
            if(null == totalFee){
                totalFee = new HashMap<>();
                totalFee.put("rechargeTotalFee", 0);
                totalFee.put("renewalTotalFee", 0);
            }
            businessOverview.putAll(totalFee);
        }
        return RespData.ok(businessOverview);
    }

    @PostMapping("/orgAccount/getDailyBreakdown")
    public RespData getDailyBreakdown(@RequestBody OrgAccountQueryDTO query){
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            query.setOrgId(orgId);
        }
        if(query.getAccountType() == 1){
            return RespData.ok(bizAccountDetailService.statisticsEveryDay(query));
        }else if(query.getAccountType() == 2){
            return RespData.ok(bizBonusDetailService.statisticsEveryDay(query));
        }else if (query.getAccountType() == 3){
            return RespData.ok(bizCommissionDetailService.statisticsEveryDay(query));
        }
        return RespData.ok();
    }

    @GetMapping("/sales/overview")
    public RespData getSalesOverview(@RequestParam Map<String, Object> params){
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        String treeStr = user.getTreeStr();
        Long orgTypeId = user.getOrgTypeId();
        List<Long> orgIds = null;
        StatisticsSalesOverviewVO result = new StatisticsSalesOverviewVO();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            String type = (String) params.get("type");
            params.put("orgId", orgId);
            if(StringUtils.isNotBlank(type) && Integer.valueOf(type) == 1) {
                orgIds = sysOrgService.getRecommendIdListByOrgId(orgId);
                params.put("orgIds", orgIds);
                if(orgIds.isEmpty()){
                    return RespData.ok(result);
                }
            }else {
                orgIds = new ArrayList<>();
                orgIds.add(orgId);
                params.put("orgIds", orgIds);
                params.put("orgIdStr", treeStr + "," + orgId);
                params.put("len", orgId.toString().length());
            }
        }
        StatisticsSalesOverviewVO commission = bizCommissionDetailService.statisticsTotalAmount(params);
        StatisticsSalesOverviewVO overview = orderInfoService.getMchSalesOverview(params);
        if(null != commission){
            result.setDirectTeamAmount(commission.getDirectTeamAmount());
            result.setOtherTeamAmount(commission.getOtherTeamAmount());
            result.setRefundAmount(commission.getRefundAmount());
            result.setSettledAmount(commission.getSettledAmount());
        }
        if(null != overview){
            result.setTradeAmount(overview.getTradeAmount());
            result.setTradeNum(overview.getTradeNum());
            result.setTransportFee(overview.getTransportFee());
            result.setServiceFee(overview.getServiceFee());
            result.setDirectTradeAmount(overview.getDirectTradeAmount());
        }
        return RespData.ok(result);
    }

    @GetMapping("/sales/listDetailPage")
    public RespData listSalesDetailPage(@RequestParam Map<String, Object> params){
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long orgTypeId = user.getOrgTypeId();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            String type = (String) params.get("type");
            if(StringUtils.isNotBlank(type) && Integer.valueOf(type) == 0){
                params.put("orgId", orgId);
            }else if(StringUtils.isNotBlank(type) && Integer.valueOf(type) == 1) {
                params.put("recommendId", orgId);
            }
        }
        PageResult page = sysOrgService.listMchSalesOverviewPage(params);
        return RespData.ok(page);
    }

    @GetMapping("/daily/salesAmount")
    public RespData salesAmount(String startTime, String endTime){
        OrderInfoQueryDTO dto = new OrderInfoQueryDTO();
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        List<StatisticsByDayVO> resultList = orderInfoService.statisticsDailySalesAmount(dto);
        return RespData.ok(resultList);
    }

    @GetMapping("/merchant/tradeDetail")
    public RespData tradeDetail(@RequestParam Map<String, Object> params){
        SysUserModel user = getUser();
        Long orgId = user.getOrgId();
        Long merchantId = Long.valueOf(params.get("merchantId").toString());
        MerchantInfoModel mchInfo = merchantInfoService.getMerchantInfoById(merchantId);
        AssertUtil.notNull(mchInfo, "商户信息不存在");
        Long orgTypeId = user.getOrgTypeId();
        if(null != orgId && !SysConstant.ROOT_ORG.equals(orgTypeId) && orgId > 0){
            AssertUtil.isTrue(mchInfo.getTreeStr().indexOf(orgId.toString()) > -1, "您无权查看不属于自己旗下的商户交易信息");
        }
        PageResult page = orderDetailService.listMerchantTradePage(params);
        return RespData.ok(page);
    }
}
