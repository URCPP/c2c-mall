
package com.diandian.dubbo.facade.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandian.dubbo.facade.common.util.SQLFilter;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author x
 * @date 2018/11/15
 */
@Data
public class PageWrapper<T> extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    /**
     * mybatis-plus分页参数
     */
    private Page<T> page;
    /**
     * 当前页码
     */
    private int currPage = 1;
    /**
     * 每页条数
     */
    private int limit = 10;

    public PageWrapper() {

    }

    public PageWrapper(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        if (params.get("curPage") != null) {
            currPage = Integer.parseInt((String) params.get("curPage"));
        }
        if (params.get("pageSize") != null) {
            limit = Integer.parseInt((String) params.get("pageSize"));
        }

        this.put("offset", (currPage - 1) * limit);
        this.put("page", currPage);
        this.put("limit", limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = SQLFilter.sqlInject((String) params.get("sidx"));
        String order = SQLFilter.sqlInject((String) params.get("order"));
        this.put("sidx", sidx);
        this.put("order", order);

        //mybatis-plus分页
        this.page = new Page<>(currPage, limit);

        //排序
        /*if (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)) {
            this.page.setOrderByField(sidx);
            this.page.setAsc("ASC".equalsIgnoreCase(order));
        }*/

    }

    public PageWrapper(PageQueryDTO dto) {
        //分页参数
        if(null != dto.getCurPage() && dto.getCurPage() > 0){
            currPage = dto.getCurPage();
        }
        if (null != dto.getPageSize() && dto.getPageSize() > 0) {
            limit = dto.getPageSize();
        }

        this.put("offset", (currPage - 1) * limit);
        this.put("page", currPage);
        this.put("limit", limit);

        //mybatis-plus分页
        this.page = new Page<>(currPage, limit);
    }

    public Page<T> getPage() {
        return page;
    }

}
