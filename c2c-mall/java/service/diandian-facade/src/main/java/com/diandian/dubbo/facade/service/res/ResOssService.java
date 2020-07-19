package com.diandian.dubbo.facade.service.res;


import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.res.MoveGroupDTO;
import com.diandian.dubbo.facade.model.res.ResOssModel;

import java.util.List;
import java.util.Map;

/**
 * 资源对象存储
 *
 * @author zzhihong
 * @date 2019/02/19
 */
public interface ResOssService{
    /**
     * 上传素材
     *
     * @param list
     * @return
     */
    List<String> upload(List<Map<String,String>> list);

    /**
     * 上传文件(一个)
     * @param urlData
     * @return
     */
    String uploadOneFile(String urlData);

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    PageResult listPage(Map<String, Object> params);
    /**
     * 通过ID更新
     *
     * @param resOssModel
     */
    void updateById(ResOssModel resOssModel);

    /**
     * 通过ID逻辑删除
     *
     * @param ids
     */
    void deleteById(List<Long> ids);

    /**
     * 移动分组
     *
     * @param moveGroupDTO
     */
    void moveGroup(MoveGroupDTO moveGroupDTO);
}
