package com.diandian.dubbo.facade.dto.biz;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 机构账户查询DTO
 * @author cjunyuan
 * @date 2019/2/20 20:36
 */
@Getter
@Setter
@ToString
public class AccountQueryDTO implements Serializable {

    private static final long serialVersionUID = 3758494158896106333L;
    private Long id;
    private Long orgId;
    public AccountQueryDTO(){}

    public AccountQueryDTO(Long id, Long orgId){
        this.id = id;
        this.orgId = orgId;
    }
}
