package com.diandian.dubbo.facade.service.member;

import com.diandian.dubbo.facade.model.member.MemberRecipientsSetModel;

/**
 * 会员收货地址设置表
 *
 * @author wbc
 * @date 2019/03/13
 */
public interface MemberRecipientsSetService {

    /**
     * @Author wubc
     * @Description // 根据会员ID 获取收货地址
     * @Date 11:12 2019/3/13
     * @Param [memberId]
     * @return com.diandian.dubbo.facade.model.member.MemberRecipientsSetModel
     **/
    MemberRecipientsSetModel getOne(Long memberId);

    /**
     * @Author wubc
     * @Description // 保存
     * @Date 11:54 2019/3/13
     * @Param [model]
     * @return java.lang.Integer
     **/
    Integer save(MemberRecipientsSetModel model);

    /**
     * @Author wubc
     * @Description // 根据ID 查
     * @Date 17:06 2019/3/13
     * @Param [id]
     * @return com.diandian.dubbo.facade.model.member.MemberRecipientsSetModel
     **/
    MemberRecipientsSetModel getById(Long id);

    /**
     * @Author wubc
     * @Description // 修改
     * @Date 17:07 2019/3/13
     * @Param [old]
     * @return java.lang.Integer
     **/
    Integer update(MemberRecipientsSetModel old);
}
