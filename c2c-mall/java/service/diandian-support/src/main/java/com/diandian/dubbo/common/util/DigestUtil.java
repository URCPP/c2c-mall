package com.diandian.dubbo.common.util;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * MD5签名工具类
 *
 * @author x
 * @date 2018/10/24
 */
@Slf4j
public class DigestUtil {

    private DigestUtil() {
    }

    public static String getSign(Map<String, Object> map, String key) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!"".equals(entry.getValue()) && null != entry.getValue()) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + key;
        log.debug("Sign Before MD5:" + result);
        result = SecureUtil.md5(result);
        log.debug("Sign Result:" + result);
        return result.toUpperCase();
    }

    /**
     * @param map
     * @param key
     * @param notContains 不包含的签名字段
     * @return
     */
    public static String getSign(Map<String, Object> map, String key, String... notContains) {
        Map<String, Object> newMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            boolean isContain = false;
            for (int i = 0; i < notContains.length; i++) {
                if (entry.getKey().equals(notContains[i])) {
                    isContain = true;
                    break;
                }
            }
            if (!isContain) {
                newMap.put(entry.getKey(), entry.getValue());
            }
        }
        return getSign(newMap, key);
    }
}
