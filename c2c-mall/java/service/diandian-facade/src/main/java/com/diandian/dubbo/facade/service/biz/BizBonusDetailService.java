package com.diandian.dubbo.facade.service.biz;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.OrgAccountQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizAccountModel;
import com.diandian.dubbo.facade.model.biz.BizBonusDetailModel;
import com.diandian.dubbo.facade.vo.OrgAccountStatementVO;

import java.util.List;
import java.util.Map;


/**
 * 奖金明细表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizBonusDetailService {

    /**
     * 分页查询
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     *
     * 功能描述: 新增
     *
     * @param bonusDetail
     * @return
     * @author cjunyuan
     * @date 2019/2/20 18:13
     */
    boolean save(BizBonusDetailModel bonusDetail);


    void issueBonus(BizBonusDetailModel bonusDetail,BizAccountModel bizAccountModel,Integer bonusType);

    /**
     *
     * 功能描述: 统计奖金流水
     *
     * @param query
     * @return
     * @author cjunyuan
     * @date 2019/3/19 11:23
     */
    List<OrgAccountStatementVO> statisticsEveryDay(OrgAccountQueryDTO query);

    /**
     *
     * 功能描述: 自定义对象分页查询
     *
     * @param query
     * @return
     * @author cjunyuan
     * @date 2019/3/19 19:54
     */
    PageResult listPage(OrgAccountQueryDTO query);
}
