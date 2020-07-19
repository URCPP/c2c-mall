package com.diandian.dubbo.facade.common.constant;

public enum FeeTypeConstant {
    //'计费类型 0按件 1按重量 2按体积',
    NUM(0),WEIGHT(1),VOLUME(2);
    private Integer value;
    FeeTypeConstant(Integer value){
        this.value=value;
    }
    public Integer getValue() {
        return this.value;
    }
}
