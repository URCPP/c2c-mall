
package com.diandian.admin.business.common.config;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @author x
 * @date 2018/09/15
 * 数据权限、参考guns实现
 * 增强查询参数
 */
@Data
public class DataScope extends HashMap {
    /**
     * 限制范围的字段名称
     */
    private String scopeName = "org_id";

    /**
     * 具体的数据范围
     */
    private List<Integer> orgIdList;

    /**
     * 是否只查询本部门
     */
    private Boolean isOnly = false;
}
