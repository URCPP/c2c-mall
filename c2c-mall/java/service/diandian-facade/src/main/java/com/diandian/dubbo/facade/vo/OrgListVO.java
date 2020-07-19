package com.diandian.dubbo.facade.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/3/1 17:40
 */
@Data
public class OrgListVO implements Serializable {

    private Long id;
    private String orgCode;
    private String orgName;
    private Long parentId;
    private String parentName;
    private String orgTypeName;
    private String contactName;
    private String phone;
    private Integer state;
    private Date createTime;
    private String recommendName;
    private Integer approveFlag;
    private List<OrgListVO> children;

    public void add(OrgListVO node) {
        if(this.children == null){
            this.children = new ArrayList<>();
        }
        this.children.add(node);
    }
}
