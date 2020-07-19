package com.diandian.dubbo.business.service.impl.merchant;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.merchant.MerchantOpenOptLogMapper;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.merchant.MerchantOpenOptLogModel;
import com.diandian.dubbo.facade.service.merchant.MerchantOpenOptLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 商户开通操作记录表
 *
 * @author jbh
 * @date 2019/02/25
 */
@Service("merchantOpenOptLogService")
@Slf4j
public class MerchantOpenOptLogServiceImpl implements MerchantOpenOptLogService {

    @Autowired
    private MerchantOpenOptLogMapper merchantOpenOptLogMapper;

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        Long merchantId = (Long) params.get("merchantId");
        Page<MerchantOpenOptLogModel> page = new PageWrapper<MerchantOpenOptLogModel>(params).getPage();
        QueryWrapper<MerchantOpenOptLogModel> qw = new QueryWrapper<>();
        qw.eq(ObjectUtil.isNotNull(merchantId), "recommend_id", merchantId);
        if (StrUtil.isNotBlank(startTime)) {
            qw.gt("create_time",  DateUtil.beginOfDay(DateUtil.parseDate(startTime)));
        }
        if (StrUtil.isNotBlank(endTime)) {
            qw.lt("create_time", DateUtil.endOfDay(DateUtil.parseDate(endTime)));
        }
        IPage<MerchantOpenOptLogModel> iPage = merchantOpenOptLogMapper.selectPage(page, qw);
        return new PageResult(iPage);
    }
}
