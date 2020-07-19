package com.diandian.dubbo.facade.service.merchant;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.merchant.MerchantInfoFormDTO;
import com.diandian.dubbo.facade.dto.merchant.MerchantInfoQueryDTO;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.vo.IntactTreeVO;
import com.diandian.dubbo.facade.vo.member.MemberExchangeHistoryLogVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantCountMemberVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantInfoDetailVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantTeamAppVo;

import java.util.List;
import java.util.Map;

/**
 * 商户信息表
 *
 * @author wbc
 * @date 2019/02/14
 */
public interface MerchantInfoService {


    /**
     * 未注册过则注册
     * @author linshuofeng/1275339166@qq.com
     * @date 2019/9/2 0002
     **/
//    MerchantInfoModel registerIfNot(String phone, String invitationCode, String wxOpenId);


    /**
     * 使用密码登录
     * @param phone
     * @param password
     * @return
     */
    MerchantInfoModel login(String phone,String password);


    /**
     * 用户注册
     * @param phone
     * @param password
     * @param invitationCode
     */
    void register(String phone,String password,String invitationCode);


    /**
     * 根据手机号码查找商户
     * @author linshuofeng/1275339166@qq.com
     * @date 2019/9/23 0023
     **/
    MerchantInfoModel getOneByPhone(String phone);



    /**
     *
     * 功能描述: 根据邀请码查询会员信息
     *
     * @param invitationCode
     * @return
     */
    MerchantInfoModel getOneByInvitationCode(String invitationCode);



    /**
     * 获取商户信息
     *
     * @param merchantId
     * @return
     */
    MerchantInfoModel getMerchantInfoById(Long merchantId);

    /**
     * 功能描述: 获取结构树下的商户
     *
     * @param list
     * @param parentId
     * @return
     * @author cjunyuan
     * @date 2019/2/27 13:34
     */
    List<IntactTreeVO> getIntactTreeMerchantPart(List<IntactTreeVO> list, Long parentId);


    /**
     * 商户列表 分页
     *
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     * 根据商户帐户获取帐户
     *
     * @param username
     * @return
     */
    MerchantInfoModel getByUsername(String username);


    /**
     * 保存商户
     *
     * @param merchantInfo
     * @param bizMerchantInfoModel
     */
//    void saveMerchant(MerchantInfoModel merchantInfo, MerchantInfoModel bizMerchantInfoModel);

    /**
     * 商户续费
     *
     * @param merchantId
     * @param renewMechantId
     */
    boolean renew(Long merchantId, Long renewMechantId);

    /**
     * 修改商户基本信息
     *
     * @param merchantInfoModel
     * @return
     */
    boolean updateById(MerchantInfoModel merchantInfoModel);

    /**
     * 删除商户基本信息
     *
     * @param id
     * @return
     */
    boolean removeById(Long id);

    /**
     * 功能描述: 代理体系后台添加商户信息
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/2 17:05
     */
//    boolean insertMerchant(MerchantInfoFormDTO dto);

    /**
     * 功能描述: 代理体系后台修改商户信息
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/2 17:05
     */
//    boolean updateMerchant(MerchantInfoFormDTO dto);

    /**
     * 功能描述: 代理体系后台商户列表
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/3/2 17:50
     */
    PageResult queryMerchantListVO(Map<String, Object> params);

    /**
     * 功能描述: 代理体系商户后台获取商户详情
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/3/2 18:16
     */
    MerchantInfoDetailVO getMerchantDetailVO(Long id);

    /**
     * 商户的会员统计
     *
     * @param merchantInfoId
     * @param type
     * @return
     */
    MerchantCountMemberVO countMember(Long merchantInfoId, String type);

    /**
     * 商户兑换券统计
     *
     * @param merchantId
     * @param type
     * @return
     */
    MemberExchangeHistoryLogVO countIntegral(Long merchantId, String type);

    /**
     * 商户认证
     *
     * @param model
     * @return
     */
//    boolean submitApprove(MerchantInfoModel model);

    /**
     * 商户开通自定义商城
     *
     * @param merchantInfo
     * @return
     */
//    boolean openCustMall(MerchantInfoModel merchantInfo);

    /**
     *
     * 功能描述: 计算数量
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/7 17:04
     */
    Integer count(MerchantInfoQueryDTO dto);

    /**
     *
     * 功能描述: 查询商户ID列表
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/7 21:07
     */
    List<Long> queryMerchantIdList(MerchantInfoQueryDTO dto);

    /**
     *
     * 功能描述: 重置密码
     *
     * @param mchId
     * @return
     * @author cjunyuan
     * @date 2019/3/30 14:56
     */
    void resetPassword(Long mchId);

    /**
     *
     * 功能描述: 导出商户数据
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/4/3 14:13
     */
    List<Map<String, Object>> getExportMchList(Map<String, Object> params);

    /**
     *
     * 功能描述: 校验商户是否正常（包括认证状态，禁用状态，过期状态）
     *
     * @param merchantId
     * @return
     * @author cjunyuan
     * @date 2019/5/15 9:45
     */
//    MerchantInfoModel apiCheckMchIsNormal(Long merchantId);

    /**
     * 将手机号码的账户 更新为钻石会员
     * @author linshuofeng/1275339166@qq.com
     * @date 2019/8/30 0030
     * @param [phone]
     **/
//    void updateDiamondSoftByPhone(String phone);

    /**
     * @author linshuofeng/1275339166@qq.com
     * @date 2019/10/10 0010
     * @param
     **/
    int updateByPhone(MerchantInfoModel merchantInfoModel);

    int count(String phone);


    List<MerchantInfoModel> getMerchantInfoByIdList(Long id, String name, String phone, Integer level, Integer delFlag);

    MerchantInfoModel getListByParentPhone(String parentPhone);

    List<MerchantTeamAppVo> getMerchantList(Long id,String phone, String name);

    Integer getByParentId(Long id);

//    Integer countAllNumber(Long id);

//    Long TotalCountDirectTeam(Long id, String phone, String name);

    PageResult listMerchantPage(Map<String, Object> params);

    PageResult listMerchantPageName(Map<String, Object> params);

    MerchantInfoModel getByShopId(Long shopId);

    MerchantInfoModel getByOrderNo(String orderNo);
}
