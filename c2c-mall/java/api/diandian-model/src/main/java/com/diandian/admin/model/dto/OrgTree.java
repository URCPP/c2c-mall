
package com.diandian.admin.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author x
 * @date 2018/09/15
 * 机构树
 */
@Getter
@Setter
@ToString
public class OrgTree extends TreeNode {
    private static final long serialVersionUID = -8638388185629856672L;
    private String name;
}
