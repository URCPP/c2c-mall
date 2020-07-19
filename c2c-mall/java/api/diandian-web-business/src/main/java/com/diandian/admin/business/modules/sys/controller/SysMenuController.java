
package com.diandian.admin.business.modules.sys.controller;

import com.diandian.admin.business.common.util.TreeUtil;
import com.diandian.admin.business.modules.sys.service.ShiroService;
import com.diandian.admin.business.modules.sys.service.SysMenuService;
import com.diandian.admin.business.modules.BaseController;
import com.diandian.admin.common.constant.SysConstant;
import com.diandian.admin.common.exception.BusinessException;
import com.diandian.admin.common.util.AssertUtil;
import com.diandian.admin.common.util.RespData;
import com.diandian.admin.model.sys.SysMenuModel;
import com.diandian.admin.model.vo.MenuTreeVO;
import com.diandian.dubbo.facade.model.sys.SysOrgModel;
import com.diandian.dubbo.facade.service.sys.SysOrgService;
import com.diandian.dubbo.facade.service.sys.SysOrgTypeMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 系统菜单
 *
 * @author x
 * @date 2018/11/08
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private ShiroService shiroService;
    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private SysOrgTypeMenuService sysOrgTypeMenuService;

    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public RespData nav() {
        List<SysMenuModel> menuList = sysMenuService.listUserMenuByUser(getUser());
        Set<String> permissions = shiroService.listUserPermissions(getUser());
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("menuList", menuList);
        resultMap.put("perms", permissions);
        return RespData.ok(resultMap);
    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public RespData list() {
        List<SysMenuModel> menuList = sysMenuService.listUserMenu(getUser());
        for (SysMenuModel sysMenuEntity : menuList) {
            SysMenuModel parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
            if (parentMenuEntity != null) {
                sysMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }
        //sysMenuService.listUserMenuByUserId(getUserId())

        return RespData.ok(menuList);
    }

    /**
     *
     * 功能描述: 菜单树
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/3/8 15:37
     */
    @GetMapping("/tree")
    @RequiresPermissions("sys:menu:list")
    public RespData tree(@RequestParam(required = false) Long orgId) {
        List<MenuTreeVO> menuList = null;
        Map<String, Object> params = new HashMap<>();
        if(null != orgId && orgId > 0){
            SysOrgModel orgModel = sysOrgService.getById(orgId);
            AssertUtil.notNull(orgModel, "机构信息不存在");
            params.put("orgTypeId", orgModel.getOrgTypeId());
        }
        menuList = sysMenuService.treeList(params);
        List<MenuTreeVO> resultList = new ArrayList<>();
        resultList.add(menuList.remove(0));
        return RespData.ok(TreeUtil.buildMenuTree(resultList, menuList, false, menuList.size()));
    }

    /**
     *
     * 功能描述: 菜单树
     *
     * @param
     * @return
     * @author cjunyuan
     * @date 2019/3/8 15:37
     */
    @GetMapping("/listTree")
    @RequiresPermissions("sys:menu:list")
    public RespData listTree() {
        List<MenuTreeVO> menuList = null;
        menuList = sysMenuService.list();
        List<MenuTreeVO> resultList = new ArrayList<>();
        resultList.add(menuList.remove(0));
        return RespData.ok(TreeUtil.buildMenuTree(resultList, menuList, false, menuList.size()));
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:menu:list")
    public RespData select() {
        //查询列表数据
        List<SysMenuModel> menuList = sysMenuService.listNotButton();
        //添加顶级菜单
        SysMenuModel root = new SysMenuModel();
        root.setId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);
        return RespData.ok(menuList);
    }

    /**
     * 菜单信息
     */
    @GetMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:list")
    public RespData info(@PathVariable("menuId") Long menuId) {
        SysMenuModel menu = sysMenuService.getById(menuId);
        return RespData.ok(menu);
    }

    /**
     * 保存
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:menu:add")
    public RespData add(@RequestBody SysMenuModel menu) {
        //数据校验
        verifyForm(menu);
        sysMenuService.save(menu);
        return RespData.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public RespData update(@RequestBody SysMenuModel menu) {
        //数据校验
        verifyForm(menu);
        sysMenuService.updateById(menu);
        return RespData.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{menuId}")
    @RequiresPermissions("sys:menu:delete")
    public RespData delete(@PathVariable("menuId") long menuId) {
        if (menuId <= 31) {
            return RespData.fail("系统菜单，不能删除");
        }

        //判断是否有子菜单或按钮
        List<SysMenuModel> menuList = sysMenuService.listByParentId(menuId);
        if (menuList.size() > 0) {
            return RespData.fail("请先删除子菜单或按钮");
        }
        if(sysOrgTypeMenuService.count(menuId, null) > 0){
            return RespData.fail("请先移除机构类型对应的菜单权限");
        }

        sysMenuService.delete(menuId);

        return RespData.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenuModel menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new BusinessException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new BusinessException("上级菜单不能为空");
        }

        //菜单
        if (SysConstant.MenuType.MENU.getValue().equals(menu.getType())) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new BusinessException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = SysConstant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenuModel parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (SysConstant.MenuType.CATALOG.getValue().equals(menu.getType()) ||
                SysConstant.MenuType.MENU.getValue().equals(menu.getType())) {
            if (parentType != SysConstant.MenuType.CATALOG.getValue()) {
                throw new BusinessException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (SysConstant.MenuType.BUTTON.getValue().equals(menu.getType())) {
            if (parentType != SysConstant.MenuType.MENU.getValue()) {
                throw new BusinessException("上级菜单只能为菜单类型");
            }
        }
    }
}
