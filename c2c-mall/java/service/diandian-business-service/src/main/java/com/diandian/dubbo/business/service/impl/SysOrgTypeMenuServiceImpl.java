package com.diandian.dubbo.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.diandian.dubbo.business.mapper.SysOrgTypeMenuMapper;
import com.diandian.dubbo.facade.model.sys.SysOrgTypeMenuModel;
import com.diandian.dubbo.facade.service.sys.SysOrgTypeMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 系统机构服务
 *
 * @author x
 * @date 2018/09/20
 */
@Service("sysOrgTypeMenuService")
public class SysOrgTypeMenuServiceImpl implements SysOrgTypeMenuService {

    @Autowired
    private SysOrgTypeMenuMapper sysOrgTypeMenuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrgMenu(Long orgTypeId, List<Long> menuIdList){
        List<Long> old = sysOrgTypeMenuMapper.queryMenuIdListByOrgTypeId(orgTypeId);
        List<Long> deleteList = new ArrayList<>(old);
        if(null == menuIdList){
            return;
        }
        deleteList.removeAll(menuIdList);
        menuIdList.removeAll(old);
        Iterator<Long> del = deleteList.iterator();
        Iterator<Long> add = menuIdList.iterator();
        while (add.hasNext()){
            SysOrgTypeMenuModel orgTypeMenu = new SysOrgTypeMenuModel();
            orgTypeMenu.setMenuId(add.next());
            orgTypeMenu.setOrgTypeId(orgTypeId);
            sysOrgTypeMenuMapper.insert(orgTypeMenu);
        }
        while (del.hasNext()){
            QueryWrapper<SysOrgTypeMenuModel> query = new QueryWrapper<>();
            query.eq("org_type_id", orgTypeId);
            query.eq("menu_id", del.next());
            sysOrgTypeMenuMapper.delete(query);
        }
    }

    @Override
    public List<Long> queryMenuIdListByOrgTypeId(long orgTypeId){
        return sysOrgTypeMenuMapper.queryMenuIdListByOrgTypeId(orgTypeId);
    }

    @Override
    public Integer count(Long menuId, Long orgTypeId){
        QueryWrapper<SysOrgTypeMenuModel> wrapper = new QueryWrapper<>();
        wrapper.eq(null != menuId && menuId > 0, "menu_id", menuId);
        wrapper.eq(null != orgTypeId && orgTypeId > 0, "org_type_id", orgTypeId);
        return sysOrgTypeMenuMapper.selectCount(wrapper);
    }
}
