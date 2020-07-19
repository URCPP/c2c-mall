package com.diandian.dubbo.facade.dto.api.query;

import com.diandian.dubbo.facade.common.constant.api.IntegralStoreConstant;
import com.diandian.dubbo.facade.common.validation.api.CustomNotBlank;
import com.diandian.dubbo.facade.dto.api.BaseDTO;
import lombok.ToString;

/**
 *
 * @author cjunyuan
 * @date 2019/5/15 13:47
 */
@ToString
public class OrderQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 4943079392015059091L;

    @CustomNotBlank(code = IntegralStoreConstant.ERROR_40015_CODE, message = IntegralStoreConstant.ERROR_40015_MESSAGE)
    private String mchOrderNo;

    public String getMchOrderNo() {
        return mchOrderNo;
    }

    public void setMchOrderNo(String mchOrderNo) {
        this.mchOrderNo = mchOrderNo;
    }
}
