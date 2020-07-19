package com.diandian.dubbo.facade.service.biz;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.biz.OrgTypeStrategyQueryDTO;
import com.diandian.dubbo.facade.model.biz.BizOrgTypeStrategyModel;

import java.util.List;
import java.util.Map;


/**
 * 机构类型策略
 *
 * @author zweize
 * @date 2019/02/19
 */
public interface BizOrgTypeStrategyService {

    /**
     * 分页查询
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     *
     * 功能描述: 根据ID查询单个
     *
     * @param id
     * @return
     * @author cjunyuan
     * @date 2019/2/20 18:11
     */
    BizOrgTypeStrategyModel getById(Long id);

    /**
     *
     * 功能描述: 根据机构类型ID获取对应的规则
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/2/20 18:06
     */
    List<BizOrgTypeStrategyModel> list(OrgTypeStrategyQueryDTO dto);

    /**
     *
     * 功能描述: 根据ID更新
     *
     * @param orgTypeStrategy
     * @return
     * @author cjunyuan
     * @date 2019/2/20 18:13
     */
    boolean updateById(BizOrgTypeStrategyModel orgTypeStrategy);

    /**
     *
     * 功能描述: 新增
     *
     * @param orgTypeStrategy
     * @return
     * @author cjunyuan
     * @date 2019/2/20 18:13
     */
    boolean save(BizOrgTypeStrategyModel orgTypeStrategy);

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
     * 功能描述: 获取单个账号
     *
     * @param dto
     * @return
     * @author cjunyuan
     * @date 2019/2/20 20:28
     */
    BizOrgTypeStrategyModel getOne(OrgTypeStrategyQueryDTO dto);

}
