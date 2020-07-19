package com.diandian.dubbo.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期操作函数
 * @author cjunyuan
 * @date 2019/3/8 11:22
 */
@Slf4j
public class DateToolUtil {

    public static List<String> getBetweenDate(Date begin, Date end){
        List<String> result = new ArrayList<>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
        while(begin.getTime()<=end.getTime()){
            result.add(SDF_DATE.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }
        return result;
    }

    public static List<String> getBetweenMonth(Date begin, Date end){
        List<String> result = new ArrayList<>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM");
        while(begin.getMonth()<=end.getMonth()){
            result.add(SDF_DATE.format(tempStart.getTime()));
            tempStart.add(Calendar.MONTH, 1);
            begin = tempStart.getTime();
        }
        return result;
    }

    public static Date parseDateTime(String date){
        SimpleDateFormat SDF_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return SDF_DATE_TIME.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDate(String date){
        SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return SDF_DATE.parse(date);
        } catch (ParseException e) {
            log.info("转换失败的日期为：" + date);
            e.printStackTrace();
        }
        return null;
    }
}
