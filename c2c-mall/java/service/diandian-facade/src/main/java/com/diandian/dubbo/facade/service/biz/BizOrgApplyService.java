package com.diandian.dubbo.facade.service.biz;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.ApplyCheckResultDTO;
import com.diandian.dubbo.facade.dto.biz.OrgApplyInfoDTO;
import com.diandian.dubbo.facade.dto.biz.OrgApplyQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizOrgApplyModel;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.vo.OrgApplyInfoDetailVO;
import com.diandian.dubbo.facade.vo.OrgApplyInfoListVO;

import java.util.Map;

/**
 * 机构申请表
 *
 * @author zweize
 * @date 2019/02/18
 */
public interface BizOrgApplyService {

    /**
     *
     * 功能描述: 添加机构申请信息
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/2/26 10:03
     */
    boolean save(OrgApplyInfoDTO dto);

    /**
     *
     * 功能描述: 修改机构申请信息
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/2/26 10:03
     */
    boolean updateById(OrgApplyInfoDTO dto);

    /**
     *
     * 功能描述: 机构申请信息分页查询
     *
     * @param params
     * @return
     * @author cjunyuan
     * @date 2019/2/20 9:49
     */
    PageResult orgApplyInfoListPage(Map<String, Object> params);

    /**
     *
     * 功能描述:
     *
     * @param checkResult
     * @return
     * @author cjunyuan
     * @date 2019/2/20 10:38
     */
    void checkOrgApplyInfo(ApplyCheckResultDTO checkResult);

    /**
     *
     * 功能描述: 获取申请信息详情
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/2/26 17:47
     */
    OrgApplyInfoDetailVO getOrgApplyInfoDetail(long id);

    /**
     *
     * 功能描述: 根据ID删除
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/2/20 18:14
     */
    boolean removeById(Long id);

    /**
     *
     * 功能描述: 根据ID查询申请信息
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/2/26 10:03
     */
    BizOrgApplyModel getById(Long id);

    /**
     *
     * 功能描述: 计算数量
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/3/7 17:13
     */
    Integer count(OrgApplyQueryDTO dto);

    /**
     *
     * 功能描述: 关闭申请
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/3/26 18:11
     */
    void closeApply(Long id);

    /**
     *
     * 功能描述: 撤消申请
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/4/1 11:12
     */
    boolean undoApply(Long id);
}
