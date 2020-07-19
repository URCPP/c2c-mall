package com.diandian.dubbo.facade.dto.api.query;

import com.diandian.dubbo.facade.dto.api.BaseDTO;
import lombok.ToString;

/**
 * 快递信息查询
 * @author cjunyuan
 * @date 2019/5/16 8:58
 */
@ToString
public class GetExpressInfoListDTO extends BaseDTO {

    private static final long serialVersionUID = 622781771664992903L;

    private String mchOrderNo;

    private String transportNo;

    public String getMchOrderNo() {
        return mchOrderNo;
    }

    public void setMchOrderNo(String mchOrderNo) {
        this.mchOrderNo = mchOrderNo;
    }

    public String getTransportNo() {
        return transportNo;
    }

    public void setTransportNo(String transportNo) {
        this.transportNo = transportNo;
    }
}
