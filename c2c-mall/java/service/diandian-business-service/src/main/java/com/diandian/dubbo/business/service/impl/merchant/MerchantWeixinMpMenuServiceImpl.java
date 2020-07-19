package com.diandian.dubbo.business.service.impl.merchant;

import cn.hutool.core.bean.BeanUtil;
import com.diandian.dubbo.business.mapper.merchant.MerchantWeixinMpMenuMapper;
import com.diandian.dubbo.facade.common.util.AssertUtil;
import com.diandian.dubbo.facade.dto.merchant.MerchantWeixinMpMenuDTO;
import com.diandian.dubbo.facade.model.merchant.MerchantWeixinMpMenuModel;
import com.diandian.dubbo.facade.service.merchant.MerchantWeixinMpMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 微信公众号菜单表 服务实现类
 * </p>
 *
 * @author cjunyuan
 * @since 2019-04-18
 */
@Service("merchantWeixinMpMenuService")
public class MerchantWeixinMpMenuServiceImpl implements MerchantWeixinMpMenuService {

    @Autowired
    private MerchantWeixinMpMenuMapper merchantWeixinMpMenuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSave(List<MerchantWeixinMpMenuDTO> menus) {
        for (MerchantWeixinMpMenuDTO dto : menus){
            MerchantWeixinMpMenuModel top = new MerchantWeixinMpMenuModel();
            BeanUtil.copyProperties(dto, top);
            if(null != dto.getId() && dto.getId() > 0){
                MerchantWeixinMpMenuModel oldWxMenu = merchantWeixinMpMenuMapper.selectById(dto.getId());
                AssertUtil.notNull(oldWxMenu, "参数错误");
                top.setParentId(0L);
                merchantWeixinMpMenuMapper.updateById(top);
            }else {
                top.setParentId(0L);
                merchantWeixinMpMenuMapper.insert(top);
            }
            for (MerchantWeixinMpMenuDTO child : dto.getChildren()){
                MerchantWeixinMpMenuModel sub = new MerchantWeixinMpMenuModel();
                BeanUtil.copyProperties(child, sub);
                if(null != child.getId() && child.getId() > 0){
                    MerchantWeixinMpMenuModel oldWxMenu = merchantWeixinMpMenuMapper.selectById(child.getId());
                    AssertUtil.notNull(oldWxMenu, "参数错误");
                    sub.setParentId(top.getId());
                    merchantWeixinMpMenuMapper.updateById(sub);
                }else {
                    sub.setParentId(top.getId());
                    merchantWeixinMpMenuMapper.insert(sub);
                }
            }
        }
    }
}
