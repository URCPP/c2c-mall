package com.diandian.dubbo.business.service.impl.member;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.business.mapper.member.MemberInfoMapper;
import com.diandian.dubbo.business.mapper.member.MemberMerchantRelationMapper;
import com.diandian.dubbo.facade.common.constant.BizConstant;
import com.diandian.dubbo.facade.common.constant.MemberConstant;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import com.diandian.dubbo.facade.dto.member.MemberInfoDTO;
import com.diandian.dubbo.facade.model.member.MemberInfoModel;
import com.diandian.dubbo.facade.model.member.MemberMerchantRelationModel;
import com.diandian.dubbo.facade.model.member.MemberOrderOptLogModel;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.model.order.OrderPayModel;
import com.diandian.dubbo.facade.service.member.MemberInfoService;
import com.diandian.dubbo.facade.service.member.MemberMerchantRelationService;
import com.diandian.dubbo.facade.service.member.MemberOrderOptLogService;
import com.diandian.dubbo.facade.service.merchant.MerchantInfoService;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.order.OrderInfoService;
import com.diandian.dubbo.facade.service.order.OrderPayService;
import com.diandian.dubbo.facade.service.support.NoGenerator;
import com.diandian.dubbo.facade.vo.MemberInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
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
@Service("memberInfoService")
@Slf4j
public class MemberInfoServiceImpl implements MemberInfoService {
    @Autowired
    private MemberInfoMapper memberInfoMapper;
    @Autowired
    private MemberMerchantRelationMapper memberMerchantRelationMapper;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private NoGenerator noGenerator;
    @Autowired
    private MemberMerchantRelationService memberMerchantRelationService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderPayService orderPayService;
    @Autowired
    private MemberOrderOptLogService memberOrderOptLogService;

    @Override
    public MemberInfoModel getMemberById(Long id) {
        return memberInfoMapper.selectById(id);
    }

    @Override
    public MemberInfoModel getByMemberAccount(String memberAccount) {
        QueryWrapper<MemberInfoModel> qw = new QueryWrapper<>();
        qw.eq("account_no", memberAccount);
        return memberInfoMapper.selectOne(qw);
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
        MerchantInfoModel merchantInfo = (MerchantInfoModel) params.get("merchantInfo");
        params.put("merchantId", merchantInfo.getId());
        String merchantId = (String) params.get("mId");
        if (StrUtil.isNotBlank(merchantId)) {
            params.put("merchantId", Long.parseLong(merchantId));
        }

        Page<MemberInfoVO> page = new PageWrapper<MemberInfoVO>(params).getPage();
        IPage<MemberInfoVO> ipage = memberInfoMapper.listPage(page, params);
        return new PageResult(ipage);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean memberRegister(MemberInfoDTO dto) {
        Long merchantId = dto.getMerchantId();
        MerchantInfoModel merchantInfoModel = merchantInfoService.getMerchantInfoById(merchantId);
        AssertUtil.notNull(merchantInfoModel, "商家未开通会员注册");

        MemberInfoModel memberInfo = memberInfoMapper.selectOne(new QueryWrapper<MemberInfoModel>().eq("account_no", dto.getMemberAcc()));
        AssertUtil.isNull(memberInfo, "账号已被注册");

        // 添加会员信息
        MemberInfoModel newMemberInfo = new MemberInfoModel();

        newMemberInfo.setPhone(dto.getPhone());
        newMemberInfo.setAvatar(dto.getAvatar());
        newMemberInfo.setNiceName(dto.getNickName());

        newMemberInfo.setAccountNo(dto.getMemberAcc());
        //  商户编号
        String code = noGenerator.getMemberNo();
        newMemberInfo.setCode(code);
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        newMemberInfo.setLoginPassword(new Sha256Hash(dto.getMemberPwd(), salt).toHex());
        newMemberInfo.setSalt(salt);
        newMemberInfo.setState(0);
        newMemberInfo.setType(dto.getType());
        newMemberInfo.setRecommendId(merchantInfoModel.getId());
        memberInfoMapper.insert(newMemberInfo);

        // 添加商户会员信息
        QueryWrapper<MemberMerchantRelationModel> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", merchantInfoModel.getId())
                .eq("member_login_name", dto.getMemberAcc());
        MemberMerchantRelationModel memberMerchantRelationModel = memberMerchantRelationMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(memberMerchantRelationModel)) {
            MemberMerchantRelationModel relationModel = new MemberMerchantRelationModel();
            relationModel.setMerchantId(dto.getMerchantId());
            relationModel.setMerchantLoginName(merchantInfoModel.getLoginName());
            relationModel.setMemberId(newMemberInfo.getId());
            relationModel.setMemberLoginName(dto.getMemberAcc());
            relationModel.setAvailBalance(BigDecimal.ZERO);
            relationModel.setFreezeBalance(BigDecimal.ZERO);
            relationModel.setConsumeTimesSum(0);
            relationModel.setExchangeCouponSum(0);
            relationModel.setExchangeCouponNum(0);
            relationModel.setShoppingCouponSum(BigDecimal.ZERO);
            relationModel.setShoppingCouponAmount(BigDecimal.ZERO);
            memberMerchantRelationMapper.insert(relationModel);
        }
        return true;
    }

    @Override
    public void resetPassword(MemberInfoDTO dto) {
        MemberInfoModel memberInfo = memberInfoMapper.selectOne(
                new QueryWrapper<MemberInfoModel>()
                        .eq("account_no", dto.getMemberAcc())
        );
        AssertUtil.notNull(memberInfo, "账号不存在");
        AssertUtil.isTrue(memberInfo.getPhone().equals(dto.getPhone()), "请输入绑定的手机号");
        String salt = RandomStringUtils.randomAlphanumeric(20);
        memberInfo.setLoginPassword(new Sha256Hash(dto.getMemberPwd(), salt).toHex());
        memberInfo.setSalt(salt);
        memberInfoMapper.updateById(memberInfo);
    }

    @Override
    public Integer getMemberExchange(Long merchantId, Long memberId) {
        return memberMerchantRelationMapper.getMemberExchange(merchantId, memberId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exchangePaySuccess(String orderNo, String tradeNo, String tradeWay){
        OrderPayModel orderPayModel = orderPayService.getByTradeOrderNo(tradeNo);
        if(null == orderPayModel){
            OrderInfoModel oldOrderInfo = orderInfoService.getByOrderNo(orderNo);
            AssertUtil.notNull(oldOrderInfo, "订单信息不存在");
            MemberOrderOptLogModel oldOptLog = memberOrderOptLogService.getOrderOptLogByOrderNo(orderNo);
            AssertUtil.notNull(oldOptLog, "会员订单信息不存在");
            MemberOrderOptLogModel updateOptLog = new MemberOrderOptLogModel();
            updateOptLog.setId(oldOptLog.getId());
            updateOptLog.setOrderState(MemberConstant.OrderState.ORDER_PAID_FREIGHT.getValue());
            memberOrderOptLogService.updateMemberOrderOptLog(updateOptLog);
            System.out.println("【会员订单记录更新成功】");
            //更新会员积分、商户余额
            memberMerchantRelationService.updateMemberAccount(orderNo);
            System.out.println("【会员积分、商户余额更新成功】");

            List<OrderDetailModel> orderDetailList = orderDetailService.listByOrderNo(orderNo);
            BigDecimal tradeAmount = BigDecimal.ZERO;
            for (OrderDetailModel orderDetail : orderDetailList){
                tradeAmount = tradeAmount.add(orderDetail.getTransportFee());
            }
            System.out.println("【积分订单运费金额】：" + tradeAmount);
            // 新增支付订单表
            OrderPayModel orderPay = new OrderPayModel();
            orderPay.setOrderId(oldOrderInfo.getId());
            orderPay.setOrderNo(oldOrderInfo.getOrderNo());
            orderPay.setTradeOrderNo(tradeNo);
            orderPay.setTradeAmount(tradeAmount);
            orderPay.setTradeWay(tradeWay);
            orderPay.setState(BizConstant.OrderPayState.PAY_SUCCESS.value());
            orderPay.setTradeTime(new Date());
            orderPayService.save(orderPay);
            System.out.println("【支付订单添加成功】");
        }
    }

    @Override
    public MemberInfoModel getByPhone(String phone) {
        MemberInfoModel memberInfoModel = memberInfoMapper.selectOne(new QueryWrapper<MemberInfoModel>()
                .eq("phone", phone));
        return memberInfoModel;
    }
}
