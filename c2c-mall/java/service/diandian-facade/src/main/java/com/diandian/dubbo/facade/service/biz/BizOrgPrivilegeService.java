package com.diandian.dubbo.facade.service.biz;

import com.diandian.dubbo.facade.dto.biz.OrgPrivilegeDTO;
import com.diandian.dubbo.facade.dto.biz.OrgPrivilegeQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizOrgPrivilegeModel;
import com.diandian.dubbo.facade.vo.OrgPrivilegeListVO;

import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/2/21 18:56
 */
public interface BizOrgPrivilegeService {

    /**
     *
     * 功能描述: 获取单个对象
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/2/21 19:00
     */
    BizOrgPrivilegeModel getOne(OrgPrivilegeQueryDTO dto);

    /**
     *
     * 功能描述: 新增
     *
     * @param orgPrivilege
     * @return
     * @author cjunyuan
     * @date 2019/2/21 19:03
     */
    boolean insert(BizOrgPrivilegeModel orgPrivilege);

    /**
     *
     * 功能描述: 根据ID更新
     *
     * @param orgPrivilege
     * @return
     * @author cjunyuan
     * @date 2019/2/20 18:13
     */
    boolean updateById(BizOrgPrivilegeModel orgPrivilege);

    /**
     *
     * 功能描述: 添加机构权益
     *
     * @param orgId
     * @param list
     * @return
     * @author cjunyuan
     * @date 2019/3/6 14:03
     */
    void saveOrgStrategy(Long orgId, List<OrgPrivilegeDTO> list);

    /**
     *
     * 功能描述: 查询机构权益列表
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/6 15:03
     */
    List<OrgPrivilegeListVO> queryOrgPrivilegeListVO(OrgPrivilegeQueryDTO dto);

    /**
     *
     * 功能描述: 查询机构类型的权益
     *
     * @param orgId
     * @param level
     * @return
     * @author cjunyuan
     * @date 2019/3/20 13:58
     */
    List<OrgPrivilegeListVO> queryOrgTypePrivilege(Long orgId, Integer level);

    /**
     *
     * 功能描述: 查询软件类型的权益
     *
     * @param orgId
     * @return
     * @author cjunyuan
     * @date 2019/3/20 13:58
     */
    List<OrgPrivilegeListVO> querySoftwareTypePrivilege(Long orgId);

    /**
     *
     * 功能描述: 回收机构权益
     *
     * @param orgId
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/29 10:31
     */
    void recycleOrgPrivilege(Long orgId, OrgPrivilegeDTO dto);

    /**
     *
     * 功能描述: 赠送机构权益
     *
     * @param orgId
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/29 10:31
     */
    void giftOrgPrivilege(Long orgId, OrgPrivilegeDTO dto);
}
