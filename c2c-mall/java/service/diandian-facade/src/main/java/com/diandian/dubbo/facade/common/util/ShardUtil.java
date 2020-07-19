package com.diandian.dubbo.facade.common.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author x
 * @date 2018-11-08
 */
public class ShardUtil {

    private static final long TWEPOCH = 1288834974657L;

    /**
     * 解析snowflake id
     *
     * @param id
     * @return
     */
    public static JSONObject resolve(long id) {
        JSONObject result = new JSONObject();
        result.put("sequence", id & IdMeta.SEQUENCE_MASK);
        result.put("workerId", (id >>> IdMeta.SEQUENCE_BITS) & IdMeta.ID_WORKER_MASK);
        result.put("centerId", (id >>> IdMeta.SEQUENCE_BITS) & IdMeta.ID_DATA_CENTER_MASK);
        result.put("timestamp", (id >>> IdMeta.TIMESTAMP_LEFT_SHIFT_BITS) & IdMeta.TIMESTAMP_MASK);
        return result;
    }

    public static String resolveYearMonth(long id) {
        long timestamp = (id >>> IdMeta.TIMESTAMP_LEFT_SHIFT_BITS) & IdMeta.TIMESTAMP_MASK;
        return DateUtil.format(new Date(timestamp + TWEPOCH), "yyMM");
    }

    /**
     * 取当前年月
     *
     * @return
     */
    public static Integer getCurrYearMonth() {

        return Integer.valueOf(DateUtil.format(new Date(), "yyMM"));
    }

    /**
     * 取上个月年月
     *
     * @return
     */
    public static Integer getLastMonth() {
        int currYearMonth = ShardUtil.getCurrYearMonth();
        try {
            DateTime yyMM = DateUtil.offsetMonth(new SimpleDateFormat("yyMM").parse(String.valueOf(currYearMonth)), -1);
            return Integer.valueOf(DateUtil.format(yyMM, "yyMM"));
        } catch (ParseException e) {
//            log.error("日期转换异常", e);
        }
        return null;
    }

    /**
     * @title 日期转分片时间
     * @author wbingcong
     * @date 2018/12/15 14:14
     */
    public static Integer dateTimeformatShareDate(String dateTime) {
        Integer currYearMonth = getCurrYearMonth();
        if (StrUtil.isNotBlank(dateTime)) {
            try {
                SimpleDateFormat sharedDay = new SimpleDateFormat("yyMM");
                SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
                Date parse = sdfDay.parse(dateTime);
                String sharedDayStr = sharedDay.format(parse);
                currYearMonth = Integer.valueOf(sharedDayStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return currYearMonth;
    }
}
