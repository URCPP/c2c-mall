package com.diandian.dubbo.facade.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 整个体系完整的树结构
 * @author cjunyuan
 * @date 2019/2/26 21:58
 */
@Data
public class IntactTreeVO implements Serializable {

    private Long id;
    private String name;
    /**
     * 对象类型，0-机构，1-商户
     */
    private Integer type;
    private Long parentId;
    private List<IntactTreeVO> children;

    public void add(IntactTreeVO node) {
        if(children == null){
            children = new ArrayList<>();
        }
        children.add(node);
    }

    public void addAll(List<IntactTreeVO> list) {
        if(children == null){
            children = new ArrayList<>();
        }
        children.addAll(list);
    }
}
