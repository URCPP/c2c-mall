package com.diandian.dubbo.facade.service.biz;

import com.diandian.dubbo.facade.model.biz.BizBankCardInformationModel;

import java.util.List;

/**
 * @description:
 * @author: wsk
 * @create: 2019-09-08 11:24
 */
public interface BizBankCardInformationService {

    BizBankCardInformationModel getById(Long id);

    List<BizBankCardInformationModel> getByShopId(Long shopId);

    void insert(BizBankCardInformationModel bizBankCardInformationModel);

    void updateById(BizBankCardInformationModel bizBankCardInformationModel);
}
