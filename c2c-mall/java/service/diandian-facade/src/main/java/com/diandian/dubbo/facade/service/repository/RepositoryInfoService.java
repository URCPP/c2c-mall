package com.diandian.dubbo.facade.service.repository;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.repository.RepositoryInfoModel;
import com.diandian.dubbo.facade.vo.RepositoryDetailVO;

import java.util.List;
import java.util.Map;

/**
 * 仓库信息
 *
 * @author zzhihong
 * @date 2019/02/22
 */
public interface RepositoryInfoService {

    /**
     * 查询所有
     * @return
     */
    List<RepositoryInfoModel> list(Long shopId);

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    RepositoryInfoModel getById(Long id);

    /**
     * 新增保存
     *
     * @param repositoryInfoModel
     */
    void save(RepositoryInfoModel repositoryInfoModel);

    /**
     * 通过ID更新
     *
     * @param repositoryInfoModel
     */
    void updateById(RepositoryInfoModel repositoryInfoModel);

    /**
     * 通过ID逻辑删除
     *
     * @param id
     */
    void logicDeleteById(Long id);

    /**
     *
     * 功能描述: 获取商品的仓库信息、运输方式信息、运输方式费用计算信息等
     *
     * @param productId
     * @param transportTypeIds
     * @return
     * @author cjunyuan
     * @date 2019/4/25 14:07
     */
    List<RepositoryDetailVO> getRepositoryDetailByProduct(Long productId, Long skuId, List<Integer> transportTypeIds);

    List<RepositoryInfoModel> getRepositoryByShopId(Long shopId);
}
