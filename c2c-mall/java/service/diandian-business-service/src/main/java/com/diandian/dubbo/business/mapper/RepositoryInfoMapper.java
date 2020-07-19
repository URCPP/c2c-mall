package com.diandian.dubbo.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandian.dubbo.facade.model.repository.RepositoryInfoModel;
import com.diandian.dubbo.facade.vo.RepositoryDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 仓库信息
 *
 * @author zzhihong
 * @date 2019/02/22
 */
public interface RepositoryInfoMapper extends BaseMapper<RepositoryInfoModel> {

    /**
     *
     * 功能描述: 查询仓库的详细信息
     *
     * @param repositoryIds
     * @return
     * @author cjunyuan
     * @date 2019/4/26 13:32
     */
    List<RepositoryDetailVO> listByRepositoryId(@Param("ids") List<Long> repositoryIds);
}
