package com.diandian.dubbo.business.service.impl.merchant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.merchant.MerchantPayfeeRecordMapper;
import com.diandian.dubbo.common.util.DateToolUtil;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.enums.MerchantConstant;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.merchant.MerchantPayFeeRecordQueryDTO;
import com.diandian.dubbo.facade.model.biz.*;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.*;
import com.diandian.dubbo.facade.model.shop.ShopInfoModel;
import com.diandian.dubbo.facade.service.biz.*;
import com.diandian.dubbo.facade.service.merchant.*;
import com.diandian.dubbo.facade.service.shop.ShopInfoService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import com.diandian.dubbo.facade.vo.StatisticsByDayVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantPayfeeRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 商户支付表
 *
 * @author zweize
 * @date 2019/03/07
 */
@Service("merchantPayfeeRecordService")
@Slf4j
public class MerchantPayfeeRecordServiceImpl implements MerchantPayfeeRecordService {

    @Autowired
    private MerchantPayfeeRecordMapper merchantPayfeeRecordMapper;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private MerchantAccountHistoryLogService merchantAccountHistoryLogService;
    @Autowired
    private MerchantAccountInfoService merchantAccountInfoService;
    @Autowired
    private BizSoftwareTypeService bizSoftwareTypeService;
    @Autowired
    private BizSoftwareTypeStrategyService bizSoftwareTypeStrategyService;
    @Autowired
    private BizAccountService bizAccountService;
    @Autowired
    private BizAccountDetailService bizAccountDetailService;
    @Autowired
    private NoGenerator noGenerator;
    @Autowired
    private MerchantWalletInfoService merchantWalletInfoService;
    @Autowired
    private MerchantWalletHistoryLogService merchantWalletHistoryLogService;
    @Autowired
    private BizMallConfigService bizMallConfigService;
    @Autowired
    private ShopInfoService shopInfoService;

    @Override
    public void save(MerchantPayfeeRecordModel merchantPayfeeRecordModel) {
        merchantPayfeeRecordMapper.insert(merchantPayfeeRecordModel);
    }

    @Override
    public MerchantPayfeeRecordModel getByPayNo(String payNo) {
        QueryWrapper<MerchantPayfeeRecordModel> qw = new QueryWrapper<>();
        qw.eq("pay_no", payNo);
        return merchantPayfeeRecordMapper.selectOne(qw);
    }

    @Override
    public List<StatisticsByDayVO> statisticsByDayVO(MerchantPayFeeRecordQueryDTO dto) {
        //列出日期之间的每一天
        List<String> dateList = DateToolUtil.getBetweenDate(DateToolUtil.parseDate(dto.getStartTime()), DateToolUtil.parseDate(dto.getEndTime()));
        List<StatisticsByDayVO> list = new ArrayList<>();
        List<StatisticsByDayVO> statisticsList = merchantPayfeeRecordMapper.statisticsByDayVO(dto);
        for (int i = 0; i < dateList.size(); i++) {
            String date = dateList.get(i);
            StatisticsByDayVO vo = new StatisticsByDayVO();
            vo.setDate(date);
            vo.setAmount(BigDecimal.ZERO);
            for (StatisticsByDayVO old : statisticsList) {
                if (null != old && date.equals(old.getDate())) {
                    vo.setAmount(old.getAmount());
                    statisticsList.remove(old);
                    break;
                }
            }
            list.add(vo);
        }
        return list;
    }

    @Override
    public Map<String, Object> statisticsTotalFee() {
        return merchantPayfeeRecordMapper.statisticsTotalFee();
    }

    @Override
    public PageResult listForPage(MerchantPayFeeRecordQueryDTO dto){
        Page<MerchantPayfeeRecordVO> page = new Page<>();
        if (null != dto.getCurPage() && dto.getCurPage() > 0) {
            page.setCurrent(dto.getCurPage());
        }
        if (null != dto.getPageSize() && dto.getPageSize() > 0) {
            page.setSize(dto.getPageSize());
        }
        IPage<MerchantPayfeeRecordVO> iPage = merchantPayfeeRecordMapper.listForPage(page, dto);
        return new PageResult(iPage);
    }
}
