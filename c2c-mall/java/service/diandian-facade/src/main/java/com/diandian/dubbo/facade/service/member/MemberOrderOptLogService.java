package com.diandian.dubbo.facade.service.member;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.member.MemberOrderOptLogModel;

import java.util.Map;

/**
 * 会员订单操作记录表
 *
 * @author wbc
 * @date 2019/02/18
 */
public interface MemberOrderOptLogService {
    /**
     * 添加
     * @param orderOptLogModel
     */
    void  insertOrderOptLog(MemberOrderOptLogModel orderOptLogModel);

    /**
     * 操作记录分页
     * @param params
     * @return
     */
    PageResult listPage(Map<String,Object> params);

    /**
     * 订单操作记录
     * @param orderNo
     * @return
     */
    MemberOrderOptLogModel getOrderOptLogByOrderNo(String orderNo);

    /**
     * 更新会员订单操作记录
     * @param memberOrderOptLogModel
     * @return
     */
    void updateMemberOrderOptLog(MemberOrderOptLogModel memberOrderOptLogModel);

    /**
     * 会员异常订单列表
     * @param params
     * @return
     */
    PageResult listMemberAbnormalOrder(Map<String, Object> params);
}
