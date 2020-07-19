
package com.diandian.admin.business.common.util;


import com.diandian.admin.business.modules.sys.vo.MenuTree;
import com.diandian.admin.model.dto.TreeNode;
import com.diandian.admin.model.sys.SysMenuModel;
import com.diandian.admin.model.vo.MenuTreeVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author x
 * @date 2018/09/15
 */
public class TreeUtil {
    /**
     * 两层循环实现建树
     *
     * @param treeNodes 传入的树节点列表
     * @return
     */
    public static <T extends TreeNode> List<T> bulid(List<T> treeNodes, Object root) {

        List<T> trees = new ArrayList<T>();

        for (T treeNode : treeNodes) {

            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }

            for (T it : treeNodes) {
                if (it.getParentId() .equals(treeNode.getId()) ) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<TreeNode>());
                    }
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public static <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<T>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public static <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId() .equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<TreeNode>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    /**
     * 通过sysMenu创建树形节点
     *
     * @param menus
     * @param root
     * @return
     */
    public static List<MenuTree> bulidTree(List<SysMenuModel> menus, Long root) {
        List<MenuTree> trees = new ArrayList<MenuTree>();
        MenuTree node;
        for (SysMenuModel menu : menus) {
            node = new MenuTree();
            node.setId(menu.getId());
            node.setParentId(menu.getParentId());
            node.setParentName(menu.getParentName());
            node.setName(menu.getName());
            node.setUrl(menu.getUrl());
            node.setCode(menu.getPerms());
            node.setLabel(menu.getName());
            node.setIcon(menu.getIcon());
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root);
    }

    /**
     * 构建菜单树
     *
     * @param resultList 结果
     * @param sourceList  源
     * @param isChildren  是否进行子元素递归
     * @return
     */
    public static List<MenuTreeVO> buildMenuTree(List<MenuTreeVO> resultList, List<MenuTreeVO> sourceList, boolean isChildren, int sourceSize) {
        for (int i = 0;i < resultList.size();i++){
            MenuTreeVO res = resultList.get(i);
            for (int j = 0;j < sourceList.size();j++){
                MenuTreeVO source = sourceList.get(j);
                if(res.getId().equals(source.getParentId())){
                    res.add(source);
                    sourceList.remove(j);
                } else if(res.getParentId().equals(source.getId())){
                    source.add(res);
                    res = sourceList.remove(j);
                    resultList.set(i, res);
                }
            }
            if(res.getChildren() != null && res.getChildren().size() > 0){
                buildMenuTree(res.getChildren(), sourceList, true, sourceList.size());
            }
        }
        //如果源List数量没有减少则目标List和源List没有交集
        if(sourceList.size() > 0 && sourceSize != sourceList.size()){
            buildMenuTree(resultList, sourceList, isChildren, sourceList.size());
        } else if (!isChildren){
            if(sourceList.size() > 0){
                resultList.add(sourceList.remove(0));
                buildMenuTree(resultList, sourceList, isChildren, sourceList.size());
            }
        }
        return resultList;
    }
}
