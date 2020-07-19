package com.diandian.dubbo.facade.dto.api.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/5/13 14:05
 */
@Getter
@Setter
@ToString
public class TokenResultDTO implements Serializable {

    private static final long serialVersionUID = -8566889949492329613L;

    private String accessToken;
    private Integer expiresIn;
}
