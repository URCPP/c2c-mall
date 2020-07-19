package com.diandian.dubbo.facade.model.biz;

import com.baomidou.mybatisplus.annotation.TableName;
import com.diandian.dubbo.facade.model.BaseModel;
import lombok.Data;

/**
 * <p>
 * 商品热搜关键字
 * </p>
 *
 * @author zzj
 * @since 2019-03-15
 */
@Data
@TableName("hot_keyword")
public class HotKeywordModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 关键字名称
     */
    private String keyword;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 (0禁用，1正常)
     */
    private Integer state;

    /**
     * 备注
     */
    private String remark;

}
