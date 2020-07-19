package com.diandian.admin.business.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.business.modules.sys.mapper.SysMenuMapper;
import com.diandian.admin.business.modules.sys.service.SysRoleMenuService;
import com.diandian.admin.business.modules.sys.mapper.SysRoleMapper;
import com.diandian.admin.business.modules.sys.mapper.SysRoleOrgMapper;
import com.diandian.admin.business.modules.sys.service.SysRoleService;
import com.diandian.admin.business.modules.sys.service.SysUserRoleService;
import com.diandian.admin.business.modules.sys.service.SysUserService;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.exception.BusinessException;
import com.diandian.admin.model.sys.SysRoleModel;
import com.diandian.admin.model.sys.SysUserModel;
import com.diandian.dubbo.facade.dto.PageResult;
import com.diandian.dubbo.facade.dto.PageWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统角色服务
 *
 * @author x
 * @date 2018/09/20
 */
@Service("sysRoleService")
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleModel> implements SysRoleService {

    @Autowired
    private SysRoleOrgMapper roleOrgMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<Long> listRoleIdListByCreater(Long createUserId) {
        return baseMapper.listRoleIdListByCreater(createUserId);
    }

    @Override
    public PageResult listPage(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");
        Long orgId = (Long) params.get("orgId");

        IPage<SysRoleModel> page = this.page(
                new PageWrapper<SysRoleModel>(params).getPage(),
                new QueryWrapper<SysRoleModel>()
                        .like(StringUtils.isNotBlank(roleName), "role_name", roleName)
                        .eq(orgId != null, "org_id", orgId)
        );
        return new PageResult(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysRole(SysRoleModel role, SysUserModel user) {
        this.save(role);
        //检查权限是否越权
        checkPrems(role, user);
        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleModel role, SysUserModel user) {
        this.updateById(role);
        //检查权限是否越权
        checkPrems(role, user);
        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatchByRoleIds(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatchByRoleIds(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatchByRoleIds(roleIds);
    }

    /**
     * 检查权限是否越权
     */
    private void checkPrems(SysRoleModel role, SysUserModel user) {
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (SysConstant.USER_SUPPER_ADMIN.equals(role.getCreateBy())) {
            return;
        }

        //查询用户所拥有的菜单列表
        List<Long> menuIdList = sysUserService.listAllMenuIdByUserId(role.getCreateBy());

        if(SysConstant.UserType.SUPER.getValue().equals(user.getType())){
            menuIdList = sysMenuMapper.listAllMenuIdByOrgId(user.getOrgId());
        }

        //判断是否越权
        if (!menuIdList.containsAll(role.getMenuIdList())) {
            throw new BusinessException("新增角色的权限，已超出你的权限范围");
        }
    }

    @Override
    public SysRoleModel getById(Long roleId) {
        return baseMapper.getById(roleId);
    }

    @Override
    public PageResult listPage(Page<SysRoleModel> page, Map<String, Object> params){
        Page<SysRoleModel> resultPage = baseMapper.listPage(page, params);
        return new PageResult(resultPage);
    }

    @Override
    public List<SysRoleModel> list(Map<String, Object> params){
        return baseMapper.list(params);
    }

    @Override
    public Set<Long> getOrgIdListByRoleIdList(List<Long> roleIds){
        return baseMapper.getOrgIdListByRoleIdList(roleIds);
    }

    /* @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertRole(SysRoleModel role) {
        Date now = new Date();
        SysRoleModel sysRole = new SysRoleModel();
        BeanUtils.copyProperties(role, sysRole);
        sysRole.setCreateTime(now);
        sysRole.setUpdateTime(now);
        baseMapper.insert(sysRole);
        SysRoleOrgModel roleOrg = new SysRoleOrgModel();
        roleOrg.setRoleId(sysRole.getId());
        roleOrg.setOrgId(Long.valueOf(role.getRoleOrgId()));
        roleOrg.setCreateTime(now);
        roleOrg.setUpdateTime(now);
        roleOrgMapper.insert(roleOrg);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRoleById(SysRoleModel role) {
        Date now = new Date();
        //删除原有的角色部门关系
        SysRoleOrgModel condition = new SysRoleOrgModel();
        condition.setRoleId(role.getId());
        roleOrgMapper.delete(new EntityWrapper<>(condition));

        //更新角色信息
        SysRoleModel sysRole = new SysRoleModel();
        BeanUtils.copyProperties(role, sysRole);
        sysRole.setUpdateTime(now);
        baseMapper.updateById(sysRole);

        //维护角色部门关系
        SysRoleOrgModel roleOrg = new SysRoleOrgModel();
        roleOrg.setRoleId(sysRole.getId());
        roleOrg.setOrgId(Long.valueOf(sysRole.getRoleOrgId()));
        roleOrg.setCreateTime(now);
        roleOrg.setUpdateTime(now);
        roleOrgMapper.insert(roleOrg);
        return true;
    }*/
}
