package com.diandian.dubbo.business.service.impl.member;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.member.MemberOrderOptLogMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.constant.MemberConstant;
import com.diandian.dubbo.facade.common.exception.DubboException;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.model.member.MemberOrderOptLogModel;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.model.order.OrderPayModel;
import com.diandian.dubbo.facade.service.member.MemberMerchantRelationService;
import com.diandian.dubbo.facade.service.member.MemberOrderOptLogService;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.service.order.OrderPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 会员订单操作记录表
 *
 * @author wbc
 * @date 2019/02/18
 */
@Service("memberOrderOptLogService")
@Slf4j
public class MemberOrderOptLogServiceImpl implements MemberOrderOptLogService {
    @Autowired
    private MemberOrderOptLogMapper orderOptLogMapper;
    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public void insertOrderOptLog(MemberOrderOptLogModel orderOptLogModel) {
        int insert = orderOptLogMapper.insert(orderOptLogModel);
        if (insert <= 0) {
            throw new DubboException("创建会员订单操作记录失败,orderNo={}", orderOptLogModel.getOrderNo());
        }
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
        Page<MemberOrderOptLogModel> page = new PageWrapper<MemberOrderOptLogModel>(params).getPage();
        IPage<MemberOrderOptLogModel> iPage = orderOptLogMapper.listPage(page, params);
        return new PageResult(iPage);
    }

    @Override
    public MemberOrderOptLogModel getOrderOptLogByOrderNo(String orderNo) {
        return orderOptLogMapper.selectOne(new QueryWrapper<MemberOrderOptLogModel>().eq("order_no", orderNo));
    }

    @Override
    public void updateMemberOrderOptLog(MemberOrderOptLogModel memberOrderOptLogModel) {
        int i = orderOptLogMapper.updateById(memberOrderOptLogModel);
        if (i <= 0) {
            throw new DubboException("更新订单操作记录数失败,orderNo={}", memberOrderOptLogModel.getOrderNo());
        }
    }

    @Override
    public PageResult listMemberAbnormalOrder(Map<String, Object> params) {
        Page<MemberOrderOptLogModel> page = new PageWrapper<MemberOrderOptLogModel>(params).getPage();
        String orderNo=(String) params.get("orderNo");
        Long memberId=(Long) params.get("memberId");
        IPage<MemberOrderOptLogModel> iPage = orderOptLogMapper.selectPage(page,
                new QueryWrapper<MemberOrderOptLogModel>()
                        .eq("merchant_id",params.get("merchantId"))
                        .eq(ObjectUtil.isNotNull(memberId),"member_id",memberId)
                        .like(StringUtils.isNotEmpty(orderNo),"order_no",orderNo)
                        .between("order_state", 1, 2)
        );
        List<MemberOrderOptLogModel> orderOptLogList = iPage.getRecords();
        orderOptLogList.forEach(orderOpt->{
            List<OrderDetailModel> orderDetailList = orderDetailService.listByOrderNo(orderOpt.getOrderNo());
            orderOpt.setOrderDetailList(orderDetailList);
        });
        return new PageResult(iPage);
    }
}
