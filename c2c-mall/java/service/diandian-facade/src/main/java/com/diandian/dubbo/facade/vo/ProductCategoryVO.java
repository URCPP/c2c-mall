package com.diandian.dubbo.facade.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author cjunyuan
 * @date 2019/4/30 17:17
 */
@Getter
@Setter
@ToString
public class ProductCategoryVO implements Serializable {

    private static final long serialVersionUID = -9021091180932703074L;

    private Long categoryId;

    private String categoryName;

    private List<Long> childIds;
}
