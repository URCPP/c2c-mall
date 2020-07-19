package com.diandian.dubbo.facade.dto.order;

import com.diandian.dubbo.facade.common.validation.StringNotIncludeBlank;
import com.diandian.dubbo.facade.common.validation.StringNotIncludeSymbol;
import com.diandian.dubbo.facade.common.validation.api.CustomNotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * 订单发货对象
 * @author cjunyuan
 * @date 2019/6/5 13:39
 */
@Getter
@Setter
@ToString
public class OrderDeliveryDTO implements Serializable {

    private Long orderDetailId;

    private String phone;

    @Valid
    private List<SendInfoDTO> list;

    @Getter
    @Setter
    @ToString
    public static class SendInfoDTO implements Serializable{

        /**
         * 运输单号
         */
        @CustomNotBlank(message = "运输单号不能为空")
        @StringNotIncludeSymbol(message = "运输单号只能包含数字和字母")
        private String transportNo;

        /**
         * 运输公司编码
         */
        private String transportCode;
    }
}
