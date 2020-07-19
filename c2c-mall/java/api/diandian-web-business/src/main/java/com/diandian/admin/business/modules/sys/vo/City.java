package com.diandian.admin.business.modules.sys.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author x
 */
@Data
public class City implements Serializable {

    String country;

    String province;

    String city;
}
