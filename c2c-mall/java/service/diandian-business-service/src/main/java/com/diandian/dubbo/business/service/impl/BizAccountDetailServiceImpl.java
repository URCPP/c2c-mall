package com.diandian.dubbo.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.BizAccountDetailMapper;
import com.diandian.dubbo.common.util.DateToolUtil;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.biz.BizAccountDetailModel;
import com.diandian.dubbo.facade.service.biz.BizAccountDetailService;
import com.diandian.dubbo.facade.service.biz.BizAccountService;
import com.diandian.dubbo.facade.dto.biz.OrgAccountQueryDTO;
import com.diandian.dubbo.facade.vo.OrgAccountDetailVO;
import com.diandian.dubbo.facade.vo.OrgAccountStatementVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 机构账号明细表
 *
 * @author zweize
 * @date 2019/02/18
 */
@Service("bizAccountDetailService")
@Slf4j
public class BizAccountDetailServiceImpl implements BizAccountDetailService {

    @Autowired
    private BizAccountDetailMapper bizAccountDetailMapper;
    @Autowired
    private BizAccountService bizAccountService;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        Date startTime = null;
        Date endTime = null;
        if (null == params.get("startTime") && StrUtil.isNotBlank(params.get("startTime").toString())){
            startTime = DateUtil.beginOfDay(DateUtil.parseDate(params.get("startTime").toString()));
        }
        if (null == params.get("endTime") && StrUtil.isNotBlank(params.get("endTime").toString())){
            endTime = DateUtil.beginOfDay(DateUtil.parseDate(params.get("endTime").toString()));
        }
        IPage<BizAccountDetailModel> page = bizAccountDetailMapper.selectPage(
                new PageWrapper<BizAccountDetailModel>(params).getPage(),
                new QueryWrapper<BizAccountDetailModel>().orderByAsc("create_time")
                        .eq(null != params.get("orgId"), "org_id", params.get("orgId"))
                        .eq(StrUtil.isNotBlank(params.get("tradeType").toString()),"trade_type",params.get("tradeType"))
                        .eq(StrUtil.isNotBlank(params.get("busType").toString()),"bus_type",params.get("busType"))
                        .ge(null != startTime,"create_time",startTime)
                        .le(null != endTime,"create_time", endTime)
        );
        return new PageResult(page);
    }

    @Override
    public boolean save(BizAccountDetailModel accountDetail) {
        Integer result = bizAccountDetailMapper.insert(accountDetail);
        return null != result && result >= 1;
    }

    @Override
    public List<OrgAccountStatementVO> statisticsEveryDay(OrgAccountQueryDTO query){
        List<String> dateList = DateToolUtil.getBetweenDate(DateToolUtil.parseDate(query.getStartTime()), DateToolUtil.parseDate(query.getEndTime()));
        if(BizConstant.StatisticalUnit.MONTH.equals(query.getUnit())){
            dateList = DateToolUtil.getBetweenMonth(DateToolUtil.parseDate(query.getStartTime()), DateToolUtil.parseDate(query.getEndTime()));
        }
        List<OrgAccountStatementVO> list = new ArrayList<>();
        if(null == query.getUnit()){
            query.setUnit(BizConstant.StatisticalUnit.DAY);
        }
        List<OrgAccountStatementVO> statisticsList = bizAccountDetailMapper.statisticsEveryDay(query);
        for (int i = 0;i < dateList.size();i++){
            String date = dateList.get(i);
            OrgAccountStatementVO vo = new OrgAccountStatementVO();
            vo.setAccountType(1);
            vo.setDate(date);
            vo.setIncomeCnt(0);
            vo.setIncomeAmount(BigDecimal.ZERO);
            vo.setExpenditureCnt(0);
            vo.setExpenditureAmount(BigDecimal.ZERO);
            for (OrgAccountStatementVO old : statisticsList){
                if(null != old && date.equals(old.getDate())){
                    BeanUtil.copyProperties(old, vo);
                    statisticsList.remove(old);
                    break;
                }
            }
            list.add(vo);
        }
        return list;
    }

    @Override
    public PageResult listPage(OrgAccountQueryDTO query){
        Page<OrgAccountDetailVO> page = new Page<>();
        if(null != query.getPageSize() && query.getPageSize() > 0){
            page.setSize(query.getPageSize());
        }
        if(null != query.getCurPage() && query.getCurPage() > 0){
            page.setCurrent(query.getCurPage());
        }
        page = bizAccountDetailMapper.orgAccountDetailListPage(page, query);
        return new PageResult(page);
    }

}
