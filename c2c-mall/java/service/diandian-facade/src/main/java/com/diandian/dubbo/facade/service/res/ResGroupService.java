package com.diandian.dubbo.facade.service.res;

import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.res.ResGroupModel;

import java.util.List;
import java.util.Map;

/**
 * 资源分组
 *
 * @author zzhihong
 * @date 2019/02/19
 */
public interface ResGroupService {

    /**
     * 查询所有
     *
     * @return
     */
    List<ResGroupModel> list(Map<String, Object> params);

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    ResGroupModel getById(Long id);

    /**
     * 新增保存
     *
     * @param productInfoModel
     */
    void save(ResGroupModel productInfoModel);

    /**
     * 通过ID更新
     *
     * @param productInfoModel
     */
    void updateById(ResGroupModel productInfoModel);

    /**
     * 通过ID逻辑删除
     *
     * @param id
     */
    void deleteById(Long id);

}
