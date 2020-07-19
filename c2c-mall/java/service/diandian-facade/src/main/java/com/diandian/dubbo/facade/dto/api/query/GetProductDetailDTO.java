package com.diandian.dubbo.facade.dto.api.query;

import com.diandian.dubbo.facade.dto.api.BaseDTO;
import lombok.ToString;

/**
 *
 * @author cjunyuan
 * @date 2019/5/14 10:36
 */
@ToString
public class GetProductDetailDTO extends BaseDTO {

    private static final long serialVersionUID = -6201114796450798031L;

    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
