package com.diandian.admin.business.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单号工具类
 *
 * @author lxiaopeng
 */
public class OrderNoTools {
    /**
     * 生成订单号
     *
     * @param orderNoCode 订单号前缀（通常表示业务）
     * @param randomNum   时间戳后的随机数位数
     * @return
     */
    public static String getOrderNo(String orderNoCode, int randomNum) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateStr = dateFormat.format(new Date());
        String number = RandomUtil.generateNumber(randomNum);
        return orderNoCode + dateStr + number;
    }

    /**
     * 生成通道账号供应商编码
     *
     * @return
     */
    public static String getChannelAccSupCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd");
        String dateStr = dateFormat.format(new Date());
        String number = RandomUtil.generateNumber(4);
        return "100" + dateStr + number;
    }
}
