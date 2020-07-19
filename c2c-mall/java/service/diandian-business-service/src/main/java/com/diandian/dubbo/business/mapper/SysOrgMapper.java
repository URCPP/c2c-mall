package com.diandian.dubbo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.sys.OrgQueryDTO;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.vo.IntactTreeVO;
import com.diandian.dubbo.facade.vo.OrgDetailVO;
import com.diandian.dubbo.facade.vo.OrgListVO;
import com.diandian.dubbo.facade.vo.StatisticsMerchantSalesVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 机构管理
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:28
 */
public interface SysOrgMapper extends BaseMapper<SysOrgModel> {

    /**
     *
     * 功能描述: 获取机构树
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/16 18:03
     */
    List<OrgListVO> queryOrgListVO(@Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 分页查询机构列表
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/3/14 18:03
     */
    Page<OrgListVO> listPage(Page<OrgListVO> page, @Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 获取机构树
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/2/16 18:04
     */
    OrgDetailVO getOrgDetailVO(@Param("id") Long id);

    /**
     *
     * 功能描述: 获取完整树的机构部分
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/16 18:04
     */
    List<IntactTreeVO> getIntactTreeOrgPart(@Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 根据ID查询名称
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/3/9 17:24
     */
    String getNameById(@Param("id") long id);

    /**
     *
     * 功能描述: 根据条件计算数量
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/27 17:41
     */
    Integer count(@Param("dto") OrgQueryDTO dto);

    /**
     *
     * 功能描述: 查询未删除的机构
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/4/1 11:57
     */
    SysOrgModel selectNotDeleteOrgById(@Param("id") Long id);

    /**
     *
     * 功能描述: 导出机构数据
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/4/3 14:13
     */
    List<Map<String, Object>> getExportOrgList(@Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 查询机构下的商户信息（用于查询商户销售概况）
     *
     * @param page
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/4/12 17:41
     */
    IPage<StatisticsMerchantSalesVO> listMchPageByOrgId(Page<StatisticsMerchantSalesVO> page, @Param("params") Map<String, Object> params);

    /**
     *
     * 功能描述: 查询机构推荐的机构ID列表
     *
     * @param orgId
     * @return
     * @author cjunyuan
     * @date 2019/4/15 14:03
     */
    List<Long> getRecommendIdListByOrgId(@Param("orgId") Long orgId);
}
