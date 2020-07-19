package com.diandian.admin.business.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diandian.admin.business.modules.sys.service.SysRoleOrgService;
import com.diandian.admin.business.modules.sys.mapper.SysRoleOrgMapper;
import com.diandian.admin.model.sys.SysRoleOrgModel;
import org.springframework.stereotype.Service;

/**
 * 系统角色机构关系服务
 *
 * @author x
 * @date 2018/09/20
 */
@Service("sysRoleOrgService")
public class SysRoleOrgServiceImpl extends ServiceImpl<SysRoleOrgMapper, SysRoleOrgModel> implements SysRoleOrgService {
}
