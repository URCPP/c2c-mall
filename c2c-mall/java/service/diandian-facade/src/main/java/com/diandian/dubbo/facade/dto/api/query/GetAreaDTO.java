package com.diandian.dubbo.facade.dto.api.query;

import com.diandian.dubbo.facade.dto.api.BaseDTO;
import lombok.ToString;

/**
 *
 * @author cjunyuan
 * @date 2019/5/13 17:27
 */
@ToString
public class GetAreaDTO extends BaseDTO {

    private static final long serialVersionUID = 5904887012051666727L;
    private Integer areaCode;

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }
}
