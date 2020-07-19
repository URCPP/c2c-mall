package com.diandian.dubbo.facade.dto.api.query;

import com.diandian.dubbo.facade.common.constant.api.IntegralStoreConstant;
import com.diandian.dubbo.facade.common.validation.api.CustomNotNull;
import com.diandian.dubbo.facade.dto.api.BaseDTO;
import lombok.ToString;

/**
 *
 * @author cjunyuan
 * @date 2019/5/14 10:12
 */
@ToString
public class GetCategoryListDTO extends BaseDTO {

    private static final long serialVersionUID = 8177347036339767789L;

    @CustomNotNull(code = IntegralStoreConstant.ERROR_40006_CODE, message = IntegralStoreConstant.ERROR_40006_MESSAGE)
    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
