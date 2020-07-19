package com.diandian.admin.business.modules.sys.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author x
 */
@Data
public class IpLocate implements Serializable {

    private String retCode;

    private City result;
}

