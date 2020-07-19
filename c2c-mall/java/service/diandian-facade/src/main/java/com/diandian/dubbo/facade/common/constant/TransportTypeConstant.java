package com.diandian.dubbo.facade.common.constant;

public enum TransportTypeConstant {
    //运输类型（0：物流快递 1：仓库自提 2：包邮 3：到付）
    EXPRESS(0), TAKE_THEIR(1),PINKAGE(2),ONESELF(3);
    private Integer value;
    TransportTypeConstant(Integer value){
        this.value=value;
    }
    public Integer getValue() {
        return this.value;
    }
}
