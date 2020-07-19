package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.ApplyCheckResultDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantOpenApplyDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantOpenApplyLogQueryDTO;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.merchant.MerchantOpenApplyLogModel;
import com.diandian.dubbo.facade.vo.merchant.MerchantOpenApplyLogDetailVO;

/**
 * 商户软件开通申请记录表
 *
 * @author wbc
 * @date 2019/02/20
 */
public interface MerchantOpenApplyLogService {

    boolean saveOpenApplyLog(MerchantOpenApplyLogModel openApplyLogModel);

    /**
     * 功能描述: 软件申请信息分页查询
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/2/20 9:49
     */
    PageResult softApplyInfoListPage(MerchantOpenApplyLogQueryDTO dto);

    /**
     * 功能描述: 软件申请信息审核
     *
     * @param checkResult
     * @return
     * @author cjunyuan
     * @date 2019/2/21 18:19
     */
    //void checkSoftApplyInfo(ApplyCheckResultDTO checkResult);

    /**
     * 获取商户申请对象
     *
     * @param id
     * @return
     */
    MerchantOpenApplyLogModel getById(Long id);

    /**
     * 功能描述: 代理体系后台添加商户申请
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/2/28 10:30
     */
    //boolean addMerchantOpenApply(MerchantOpenApplyDTO dto);

    /**
     * 功能描述: 代理体系后台添加商户申请
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/2/28 10:30
     */
    boolean updateById(MerchantOpenApplyDTO dto);

    /**
     * 功能描述: 查询商户申请详情
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/2/28 10:30
     */
    MerchantOpenApplyLogDetailVO getMerchantApplyDetail(MerchantOpenApplyLogQueryDTO dto);

    /**
     *
     * 功能描述: 添加商户开通奖励（与开通商户奖励不一样）
     *
     * @param merchantInfo
     * @return
     * @author cjunyuan
     * @date 2019/3/27 11:12
     */
    //void addGiftReward(MerchantInfoModel merchantInfo);

    /**
     *
     * 功能描述: 添加开通商户奖励
     *
     * @param merchantInfo
     * @return
     * @author cjunyuan
     * @date 2019/3/27 10:51
     */
    void addOpenMerchantReward(MerchantInfoModel merchantInfo);

    /**
     * 功能描述: 添加机构推荐软件奖励（推荐软件奖励目前只有奖励首次开通年费）
     *
     * @param merchantInfo
     * @return
     * @author cjunyuan
     * @date 2019/2/22 9:06
     */
    void addOrgRecommendReward(MerchantInfoModel merchantInfo);

    /**
     * 功能描述: 添加商户推荐奖励
     *
     * @param merchantInfo
     * @return
     * @author cjunyuan
     * @date 2019/2/22 10:24
     */
    //void addMerchantRecommendReward(MerchantInfoModel merchantInfo);

    /**
     * 功能描述: 计算数量
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/7 17:17
     */
    Integer count(MerchantOpenApplyLogQueryDTO dto);

    /**
     * 根据商户ID  和 软件类型ID 查询
     *
     * @param merchantId
     * @param softTypeId
     * @return
     */
    MerchantOpenApplyLogModel getByMerIdAndSoftId(Long merchantId, Long softTypeId);

    /**
     * 更新记录
     *
     * @param openApplyLogModel
     * @return
     */
    boolean update(MerchantOpenApplyLogModel openApplyLogModel);

    /**
     *
     * 功能描述: 关闭商户申请
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/3/26 18:39
     */
    void closeApply(Long id);

    /**
     *
     * 功能描述: 撤消申请
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/4/1 14:59
     */
    //void undoApply(Long id);
}
