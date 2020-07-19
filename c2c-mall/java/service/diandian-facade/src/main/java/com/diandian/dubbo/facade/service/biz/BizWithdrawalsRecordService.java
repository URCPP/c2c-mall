package com.diandian.dubbo.facade.service.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.model.biz.BizWithdrawalsRecordModel;

import java.util.Map;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-10 21:03
 */
public interface BizWithdrawalsRecordService {

    PageResult listPage(Map<String, Object> params);

    BizWithdrawalsRecordModel getById(Long id);

    void insert(BizWithdrawalsRecordModel bizWithdrawalsRecordModel);

    void updateById(BizWithdrawalsRecordModel bizWithdrawalsRecordModel);

    void delete(BizWithdrawalsRecordModel bizWithdrawalsRecordModel);

    void withdrawal(BizWithdrawalsRecordModel bizWithdrawalsRecordModel);

    void examine(BizWithdrawalsRecordModel bizWithdrawalsRecordModel);
}
