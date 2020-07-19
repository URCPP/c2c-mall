package com.diandian.dubbo.facade.service.sys;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.sys.OrgFormDTO;
import com.diandian.dubbo.facade.dto.sys.OrgQueryDTO;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.vo.IntactTreeVO;
import com.diandian.dubbo.facade.vo.OrgDetailVO;
import com.diandian.dubbo.facade.vo.OrgListVO;

import java.util.List;
import java.util.Map;

/**
 * 机构管理
 *
 * @author x
 * @email qzyule@qq.com
 * @date 2018-09-05 17:20:28
 */
public interface SysOrgService {

    /**
     * 查询部门树菜单
     * @param params
     * @return 树
     */
    List<OrgListVO> listTree(Map<String, Object> params);

    /**
     *
     * 功能描述: 分页查询机构列表
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/3/14 18:01
     */
    PageResult listPage(Map<String, Object> params);

    /**
     * 获取机构信息
     * @param id
     * @return
     */
    OrgDetailVO getOrgDetailVO(Long id);

    /**
     * 添加机构信息
     * @param dto
     * @return
     */
    Boolean insertOrg(OrgFormDTO dto);

    /**
     * 更新部门
     * @param dto 部门信息
     * @return 成功、失败
     */
    Boolean updateOrgById(OrgFormDTO dto);

    /**
     *
     * 功能描述: 根据ID查询
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/2/22 15:32
     */
    SysOrgModel getById(Long id);
    
    /**
     *
     * 功能描述: 根据条件计算数量
     *
     * @param dto
     * @return 
     * @author cjunyuan
     * @date 2019/2/22 15:43
     */
    Integer count(OrgQueryDTO dto);

    /**
     *
     * 功能描述: 根据条件计算数量（表连接方式）
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/2/22 15:43
     */
    Integer unionCount(OrgQueryDTO dto);

    /**
     *
     * 功能描述: 获取完整树的机构部分
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/22 15:43
     */
    List<IntactTreeVO> getIntactTreeOrgPart(Map<String, Object> params);

    /**
     *
     * 功能描述: 根据ID查询
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/2/22 15:32
     */
    SysOrgModel getOne(OrgQueryDTO dto);

    /**
     *
     * 功能描述: 导出机构数据
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/4/3 14:13
     */
    List<Map<String, Object>> getExportOrgList(Map<String, Object> params);

    /**
     *
     * 功能描述: 查询机构下的商户销售概况信息
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/4/12 17:41
     */
    PageResult listMchSalesOverviewPage(Map<String, Object> params);

    /**
     *
     * 功能描述: 查询机构推荐的机构ID列表
     *
     * @param orgId
     * @return
     * @author cjunyuan
     * @date 2019/4/15 14:03
     */
    List<Long> getRecommendIdListByOrgId(Long orgId);
}

