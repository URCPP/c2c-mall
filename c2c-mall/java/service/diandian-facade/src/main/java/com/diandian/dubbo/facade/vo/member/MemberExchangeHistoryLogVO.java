package com.diandian.dubbo.facade.vo.member;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jbuhuan
 * @date 2019/3/4 16:25
 */
@Data
public class MemberExchangeHistoryLogVO implements Serializable {
    /**
     * 发放的兑换券数量
     */
    private Long giveOutExchangeNum;
    /**
     * 消耗的兑换券数量
     */
    private Long consumeExchangeNum;
    /**
     * 累计发放兑换券数量
     */
    private Long addUpGiveOutExchangeNum;
    /**
     * 累计消耗的兑换券数量
     */
    private Long addUpConsumeExchangeNum;

    private JSONArray dataX;
    private JSONArray dataGiveOut;
    private JSONArray dataConsume;
}
