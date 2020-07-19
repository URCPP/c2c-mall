package com.diandian.admin.merchant.modules.shop.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.merchant.modules.shop.mapper.ShopTypeMapper;
import com.diandian.admin.merchant.modules.shop.service.ShopTypeService;
import com.diandian.admin.model.shop.ShopTypeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 店铺类型
 *
 * @author zzhihong
 * @date 2019/02/26
 */
@Service("shopTypeService")
@Slf4j
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopTypeModel> implements ShopTypeService {

    @Autowired
    private ShopTypeMapper shopTypeMapper;


}
