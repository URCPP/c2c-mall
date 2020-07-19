package com.diandian.dubbo.facade.service.biz;

import com.diandian.dubbo.facade.model.biz.BizConfigModel;
import com.diandian.dubbo.facade.vo.BizConfigVO;

/**
 * 平台总配置表
 *
 * @author jbh
 * @date 2019/03/14
 */
public interface BizConfigService {

    /**
     * @return com.diandian.dubbo.facade.model.biz.BizConfigModel
     * @Author wubc
     * @Description // 获取信息
     * @Date 10:56 2019/3/14
     * @Param []
     **/
    BizConfigModel getOne();

    /**
     * 保存
     * @author: jbuhuan
     * @date: 2019/3/14 15:28
     * @param vo
     * @return: R
     */
    void save(BizConfigVO vo);
}
