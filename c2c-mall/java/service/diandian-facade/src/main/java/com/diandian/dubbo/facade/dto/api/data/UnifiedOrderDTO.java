package com.diandian.dubbo.facade.dto.api.data;

import com.diandian.dubbo.facade.common.constant.api.IntegralStoreConstant;
import com.diandian.dubbo.facade.common.validation.api.CustomMin;
import com.diandian.dubbo.facade.common.validation.api.CustomNotBlank;
import com.diandian.dubbo.facade.common.validation.api.CustomNotNull;
import com.diandian.dubbo.facade.dto.api.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/5/14 17:08
 */
@Getter
@Setter
@ToString
public class UnifiedOrderDTO extends BaseDTO {

    private static final long serialVersionUID = -8944001195237778113L;

    private List<ApiOrderProduct> orderProductsList;
    private String orderProducts;
    @CustomNotNull(code = IntegralStoreConstant.ERROR_40009_CODE, message = IntegralStoreConstant.ERROR_40009_MESSAGE)
    private Integer provinceCode;
    @CustomNotNull(code = IntegralStoreConstant.ERROR_40010_CODE, message = IntegralStoreConstant.ERROR_40010_MESSAGE)
    private Integer cityCode;
    @CustomNotNull(code = IntegralStoreConstant.ERROR_40011_CODE, message = IntegralStoreConstant.ERROR_40011_MESSAGE)
    private Integer areaCode;
    @CustomNotBlank(code = IntegralStoreConstant.ERROR_40012_CODE, message = IntegralStoreConstant.ERROR_40012_MESSAGE)
    private String address;
    @CustomNotBlank(code = IntegralStoreConstant.ERROR_40013_CODE, message = IntegralStoreConstant.ERROR_40013_MESSAGE)
    private String name;
    @CustomNotBlank(code = IntegralStoreConstant.ERROR_40014_CODE, message = IntegralStoreConstant.ERROR_40014_MESSAGE)
    private String phone;
    @CustomNotBlank(code = IntegralStoreConstant.ERROR_40015_CODE, message = IntegralStoreConstant.ERROR_40015_MESSAGE)
    private String mchOrderNo;

    @Getter
    @Setter
    @ToString
    public static class ApiOrderProduct implements Serializable{
        private Integer num;
        private String price;
        private Long productId;
    }
}
