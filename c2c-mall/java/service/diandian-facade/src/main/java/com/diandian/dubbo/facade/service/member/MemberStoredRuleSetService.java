package com.diandian.dubbo.facade.service.member;

import com.diandian.dubbo.facade.model.member.MemberStoredRuleSetModel;

import java.util.List;
import java.util.Map;

/**
 * 会员储值设置表
 *
 * @author wbc
 * @date 2019/02/15
 */
public interface MemberStoredRuleSetService {

    /**
     * 添加设置
     *
     * @param memberStoredRuleSetModel
     * @return
     */
    boolean insertSet(MemberStoredRuleSetModel memberStoredRuleSetModel);

    /**
     * 修改设置
     *
     * @param memberStoredRuleSetModel
     * @return
     */
    boolean updateSet(MemberStoredRuleSetModel memberStoredRuleSetModel);

    /**
     * 设置列表
     *
     * @param params
     * @return
     */
    List<MemberStoredRuleSetModel> listSets(Map<String, Object> params,Long merchantInfoId);

    /**
     *
     * @param id
     * @return
     */
    MemberStoredRuleSetModel getSetById(Long id);


}
