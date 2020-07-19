package com.diandian.dubbo.facade.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * 功能描述: 分页查询DTO
 *
 * @param
 * @return
 * @author cjunyuan
 * @date 2019/6/28 9:27
 */
@Getter
@Setter
@ToString
public class PageQueryDTO implements Serializable {

    private Integer curPage;

    private Integer pageSize;
}
