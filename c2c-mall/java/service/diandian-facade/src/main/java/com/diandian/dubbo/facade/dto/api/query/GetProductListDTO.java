package com.diandian.dubbo.facade.dto.api.query;

import com.diandian.dubbo.facade.dto.api.BaseDTO;
import lombok.ToString;

/**
 *
 * @author cjunyuan
 * @date 2019/5/14 10:36
 */
@ToString
public class GetProductListDTO extends BaseDTO {

    private static final long serialVersionUID = -4150467178384659898L;

    private Integer page;
    private Integer pageSize;
    private Long categoryId;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
