package com.diandian.dubbo.facade.dto.biz;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 商品信息查询对象
 * @author cjunyuan
 * @date 2019/5/7 10:01
 */
@Getter
@Setter
@ToString
public class ProductInfoQueryDTO implements Serializable {

    private Long brandId;

    private List<Long> categoryIds;
}
