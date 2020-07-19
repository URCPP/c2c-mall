package com.diandian.admin.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author x
 * @date 2018/09/15
 */
@Getter
@Setter
@ToString
public class TreeNode implements Serializable {
    protected Long id;
    protected Long parentId;
    protected String parentName;
    protected List<TreeNode> children = new ArrayList<TreeNode>();

    public void add(TreeNode node) {
        children.add(node);
    }
}
