package com.diandian.dubbo.facade.dto.biz;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 机构策略查询DTO
 * @author cjunyuan
 * @date 2019/2/20 19:13
 */
@Getter
@Setter
@ToString
public class OrgTypeStrategyQueryDTO implements Serializable {

    private static final long serialVersionUID = 7294906018187773612L;

    private Long orgTypeId;
    private Integer strategyType;
    private Integer rewardType;

    public OrgTypeStrategyQueryDTO(){}

    public OrgTypeStrategyQueryDTO(Long orgTypeId, Integer strategyType, Integer rewardType){
        this.orgTypeId = orgTypeId;
        this.strategyType = strategyType;
        this.rewardType = rewardType;
    }
}
