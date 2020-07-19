
package com.diandian.admin.merchant.modules.sys.vo;

import com.diandian.admin.model.dto.TreeNode;
import com.diandian.admin.model.sys.SysMenuModel;
import lombok.Data;

/**
 * @author x
 * @date 2018/09/15
 */
@Data
public class MenuTree extends TreeNode {
    private String icon;
    private String name;
    private String url;
    private boolean spread = false;
    private String path;
    private String component;
    private String authority;
    private String redirect;
    private String code;
    private Integer type;
    private String label;
    private Integer sort;

    public MenuTree() {
    }

    public MenuTree(Long id, String name, Long parentId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.label = name;
    }

    public MenuTree(Long id, String name, MenuTree parent) {
        this.id = id;
        this.parentId = parent.getId();
        this.name = name;
        this.label = name;
    }

    public MenuTree(SysMenuModel menu) {
        this.id = menu.getId();
        this.parentId = menu.getParentId();
        this.icon = menu.getIcon();
        this.name = menu.getName();
        this.url = menu.getUrl();
        this.type = menu.getType();
        this.label = menu.getName();
        this.sort = menu.getSort();
    }
}
