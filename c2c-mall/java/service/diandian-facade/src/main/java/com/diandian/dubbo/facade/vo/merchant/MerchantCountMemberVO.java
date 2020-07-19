package com.diandian.dubbo.facade.vo.merchant;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jbuhuan
 * @date 2019/2/27 17:10
 */
@Data
public class MerchantCountMemberVO implements Serializable {

    /**
     * 累积会员
     */
    private Integer totalMember;

    /**
     * 新增会员
     */
    private Integer newMember;

    /**
     * 活跃会员
     */
    private Integer activeMember;

    /**
     * 图表横坐标
     */
    private JSONArray dataX;

    /**
     * 图表数据
     */
    private JSONArray data;
}
