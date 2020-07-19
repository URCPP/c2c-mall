package com.diandian.dubbo.business.mapper.merchant;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.merchant.MerchantInfoQueryDTO;
import com.diandian.dubbo.facade.model.member.MerchantInfoModel;
import com.diandian.dubbo.facade.model.order.OrderInfoModel;
import com.diandian.dubbo.facade.vo.IntactTreeVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantInfoDetailVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantInfoListVO;
import com.diandian.dubbo.facade.vo.merchant.MerchantTeamAppVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 商户信息表
 *
 * @author wbc
 * @date 2019/02/14
 */
public interface MerchantInfoMapper extends BaseMapper<MerchantInfoModel> {

    /**
     *
     * 功能描述:
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/27 13:48
     */
    List<IntactTreeVO> getIntactTreeMerchantPart(@Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 代理体系后台获取商户列表
     *
     * @param params
     * @param page
     * @return
     * @author cjunyuan
     * @date 2019/3/2 17:54
     */
    IPage<MerchantInfoListVO> queryMerchantListVO(Page<MerchantInfoListVO> page, @Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 获取商户详情
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/2/16 18:04
     */
    MerchantInfoDetailVO getMerchantDetailVO(@Param("id") Long id);

    /**
     *
     * 功能描述: 查询商户ID列表
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/7 21:07
     */
    List<Long> queryMerchantIdList(@Param("dto") MerchantInfoQueryDTO dto);

    /**
     *
     * 功能描述: 根据ID查名称
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/3/9 17:27
     */
    String getNameById(@Param("id") long id);

    /**
     * @Author wubc
     * @Description // 根据用户名查询
     * @Date 14:14 2019/3/19
     * @Param [username]
     * @return com.diandian.dubbo.facade.model.member.MerchantInfoModel
     **/
    MerchantInfoModel getByUsername(String username);

    /**
     *
     * 功能描述: 导出商户数据
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/4/3 14:13
     */
    List<Map<String, Object>> getExportMchList(@Param("params") Map<String, Object> params);


    /**
     *
     * 功能描述: 根据条件计算数量
     *
     * @param phone
     * @param inviteCode
     * @return
     * @author cjunyuan
     * @date 2019/7/18 15:31
     */
    Integer count(@Param("phone") String phone, @Param("inviteCode") String inviteCode);


    List<MerchantInfoModel> getMerchantInfoByIdList(@Param("id")Long id, @Param("name")String name,@Param("phone") String phone,
                                                    @Param("level")Integer level, @Param("delFlag")Integer delFlag);

    MerchantInfoModel getListByParentPhone(String parentPhone);

    List<MerchantTeamAppVo> getMerchantList(@Param("id")Long id,@Param("phone")String phone, @Param("name") String name);

    Integer getByParentId(@Param("id")Long id);

//    Integer countAllNumber(@Param("id")Long id);


//    Long TotalCountDirectTeam(@Param("id")Long id, @Param("name")String name,@Param("phone") String phone);


    IPage<MerchantInfoModel> listMerchantPage(Page<MerchantInfoModel> page, @Param("params")Map<String, Object> params);

    IPage<MerchantInfoModel> listMerchantPageName(Page<MerchantInfoModel> page, @Param("params")Map<String, Object> params);

    MerchantInfoModel getByShopId(@Param("shopId")Long shopId);

    MerchantInfoModel getByOrderNo(@Param("orderNo")String orderNo);
}
