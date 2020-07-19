package com.diandian.dubbo.facade.dto.biz;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author cjunyuan
 * @date 2019/3/6 13:54
 */
@Getter
@Setter
@ToString
public class OrgPrivilegeDTO implements Serializable {

    private static final long serialVersionUID = -265518796365221705L;

    private Integer rewardTypeId;
    private String rewardTypeName;
    private Long rewardOrgTypeId;
    private Long rewardSoftwareTypeId;
    private BigDecimal rewardValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        OrgPrivilegeDTO that = (OrgPrivilegeDTO) o;
        if(Objects.equals(rewardTypeId, that.rewardTypeId) && Objects.equals(rewardOrgTypeId, that.rewardOrgTypeId)){
            return true;
        }
        if(Objects.equals(rewardTypeId, that.rewardTypeId) && Objects.equals(rewardSoftwareTypeId, that.rewardSoftwareTypeId)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        if(null != rewardOrgTypeId){
            return Objects.hash(rewardTypeId, rewardOrgTypeId);
        }
        return Objects.hash(rewardTypeId, rewardSoftwareTypeId);
    }
}
