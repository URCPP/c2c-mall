package com.diandian.admin.model.vo;

import com.diandian.admin.model.dto.TreeNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树VO对象
 * @author cjunyuan
 * @date 2019/3/8 14:48
 */
@Getter
@Setter
@ToString
public class MenuTreeVO implements Serializable {

    private static final long serialVersionUID = -1780659208111686608L;

    private Long id;

    private String name;

    private Long parentId;

    private List<MenuTreeVO> children;

    public void add(MenuTreeVO node) {
        if(null == children){
            children = new ArrayList<>();
        }
        children.add(node);
    }
}
