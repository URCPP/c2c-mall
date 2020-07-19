package com.diandian.admin.business.modules.sys.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author x
 * @date 2018-11-09
 */
@Data
public class TokenVO {

    public TokenVO() {
    }

    public TokenVO(String token, Date expireTime) {
        this.token = token;
        this.expireTime = expireTime;
    }

    /**
     * token
     */
    private String token;

    /**
     * 过期时间
     */
    private Date expireTime;
}
