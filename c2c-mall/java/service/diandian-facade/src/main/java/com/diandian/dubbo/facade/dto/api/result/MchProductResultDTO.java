package com.diandian.dubbo.facade.dto.api.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/5/14 10:38
 */
@Getter
@Setter
@ToString
public class MchProductResultDTO implements Serializable {

    private static final long serialVersionUID = -774430240245715885L;

    private Long productId;
    private String productName;
    private String price;
    private Integer exchangeIntegral;
    private Long categoryId;
    private String weight;
    private String volume;
    private String productHtml;
    private String serviceRate;
    private String transportCompany;
    private String transportName;
    private String transportRemark;
    private String lastUpdateTime;
    private Integer productState;
    private List<String> thumbUrls;
}
