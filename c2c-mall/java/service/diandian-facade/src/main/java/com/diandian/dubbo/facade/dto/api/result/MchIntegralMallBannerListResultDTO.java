package com.diandian.dubbo.facade.dto.api.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/5/17 14:52
 */
@Getter
@Setter
@ToString
public class MchIntegralMallBannerListResultDTO implements Serializable {

    private static final long serialVersionUID = -7732771597860549269L;

    private String imageUrl;
    private String linkUrl;
}
