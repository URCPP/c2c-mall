package com.diandian.dubbo.facade.dto.api.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author cjunyuan
 * @date 2019/5/14 10:16
 */
@Getter
@Setter
@ToString
public class CategoryListResultDTO implements Serializable {

    private Long categoryId;

    private Long parentId;

    private String categoryName;

    private String imageUrl;
}
