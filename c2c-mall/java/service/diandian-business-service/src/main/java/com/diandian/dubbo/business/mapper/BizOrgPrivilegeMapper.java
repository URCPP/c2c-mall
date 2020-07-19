package com.diandian.dubbo.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.dto.biz.OrgPrivilegeDTO;
import com.diandian.dubbo.facade.dto.biz.OrgPrivilegeQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizOrgPrivilegeModel;
import com.diandian.dubbo.facade.vo.OrgPrivilegeListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构特权信息表
 * @author cjunyuan
 * @date 2019/2/20 18:50
 */
public interface BizOrgPrivilegeMapper extends BaseMapper<BizOrgPrivilegeModel> {

    /**
     *
     * 功能描述: 根据机构ID查询机构权益列表
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/6 14:50
     */
    List<OrgPrivilegeListVO> queryOrgPrivilegeListVO(@Param("dto") OrgPrivilegeQueryDTO dto);

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
    List<OrgPrivilegeListVO> queryOrgTypePrivilege(@Param("orgId") Long orgId, @Param("level") Integer level);

    /**
     *
     * 功能描述: 查询软件类型的权益
     *
     * @param orgId
     * @return
     * @author cjunyuan
     * @date 2019/3/20 13:58
     */
    List<OrgPrivilegeListVO> querySoftwareTypePrivilege(@Param("orgId") Long orgId);

    /**
     *
     * 功能描述: 查询机构所有类型（机构和软件）的权益（批量保存用）
     *
     * @param orgId
     * @return
     * @author cjunyuan
     * @date 2019/3/23 14:13
     */
    List<OrgPrivilegeDTO> queryOrgAllTypePrivilegeList(@Param("orgId") Long orgId);
}
