package com.diandian.dubbo.facade.dto.api.query;

import com.diandian.dubbo.facade.dto.api.BaseDTO;
import lombok.ToString;

/**
 *
 * @author cjunyuan
 * @date 2019/5/13 17:27
 */
@ToString
public class GetAreaListDTO extends BaseDTO {

    private static final long serialVersionUID = 8687069509200869893L;
    private Integer page;
    private Integer pageSize;
    private Integer parentCode;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = (null == page ? 1 : page);
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = (null == pageSize ? 10 : pageSize);
    }

    public Integer getParentCode() {
        return parentCode;
    }

    public void setParentCode(Integer parentCode) {
        this.parentCode = parentCode;
    }
}
