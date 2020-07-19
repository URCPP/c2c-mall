package com.diandian.dubbo.facade.vo.merchant;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/3/2 17:48
 */
@Data
public class MerchantInfoListVO implements Serializable {

    private static final long serialVersionUID = 4390542578961096219L;

    private Long id;
    private String code;
    private String name;
    private Long parentId;
    private String parentName;
    private Long softTypeId;
    private String softTypeName;
    private String leader;
    private String phone;
    private String loginName;
    private Integer approveFlag;
    private Integer disabledFlag;
    private Date createTime;
    private Date merchantExpireTime;
    private Integer mallOpenFlag;
    private List<MerchantInfoListVO> children;

    public void add(MerchantInfoListVO node) {
        if(this.children == null){
            this.children = new ArrayList<>();
        }
        this.children.add(node);
    }
}
