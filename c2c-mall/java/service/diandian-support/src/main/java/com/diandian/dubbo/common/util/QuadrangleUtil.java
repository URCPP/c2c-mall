package com.diandian.dubbo.common.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author x
 * @date 2018-12-13
 */
@Slf4j
public class QuadrangleUtil {


    /**
     * 验证签名
     *
     * @param params 参数
     * @param key    签名key
     * @return 成功 true 失败 false
     */
    public static boolean validatePaySign(Map<String, Object> params, String key) {
        if (null == params || StrUtil.isBlank(key) || null == params.get("sign")) {
            return false;
        }
        // 签名
        String sign = (String) params.get("sign");
        // 不参与签名
        params.remove("sign");
        String checkSign = DigestUtil.getSign(params, key);
        params.put("sign", sign);
        log.info("正确网关签名:{}", checkSign);
        return checkSign.equalsIgnoreCase(sign);
    }


    /**
     * 判断某一时间是否在一个区间内
     *
     * @param sourceTime 时间区间,全闭合,如[10:00-20:00)
     * @param curTime    需要判断的时间 如10:00
     * @return
     * @throws IllegalArgumentException
     */
    public static boolean isInTime(String sourceTime, String curTime) {
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
        if (curTime == null || !curTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
        }
        String[] args = sourceTime.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            long now = sdf.parse(curTime).getTime();
            long start = sdf.parse(args[0]).getTime();
            long end = sdf.parse(args[1]).getTime();
            if ("00:00".equals(args[1])) {
                args[1] = "24:00";
            }
            if (end < start) {
                return now > end && now < start;
            } else {
                return now >= start && now <= end;
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
    }


    public static String getCurrDate() {
        return DateUtil.format(new Date(), "yyyy-MM-dd");
    }


    public static String getTomorrowDate(String currDate) {
        DateTime offsetDay = DateUtil.offsetDay(DateUtil.parse(currDate, "yyyy-MM-dd"), 1);
        return DateUtil.format(offsetDay, "yyyy-MM-dd");
    }
}
