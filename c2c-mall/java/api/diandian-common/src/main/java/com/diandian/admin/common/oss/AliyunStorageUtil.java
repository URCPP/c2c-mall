package com.diandian.admin.common.oss;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * @author zzhihong
 * @date 2018-09-20
 */

@Slf4j
public class AliyunStorageUtil {

    private final static String BASE64_IMG_PRFIX = "data:image/";

    /**
     * 上传BAE64图片
     *
     * @param prefix       上传前缀 一般指文件夹名
     * @param base64ImgStr 图片base64编码
     * @return 文件路径
     */

    public static String uploadBase64Img(String prefix, String base64ImgStr) {
        if (StrUtil.isNotBlank(base64ImgStr) && base64ImgStr.startsWith(BASE64_IMG_PRFIX)) {
            String path = AliyunStorageFactory.build().uploadBase64Img(prefix, base64ImgStr);
            log.info("阿里OSS图片上传成功,path={}", path);
            return path;
        }
        return null;
    }

    /**
     * 上传文件
     * @param prefix 前缀 按业务区分文件夹用
     * @param inputStream 文件流
     * @param suffix 文件扩展名  如 .jpg 前面要带 .
     * @return path 路径
     */

    public static String uploadFile(String prefix,InputStream inputStream,String suffix){
        if (null == inputStream) {
            return null;
        }
        String path = AliyunStorageFactory.build().uploadInputStream(prefix, inputStream, suffix);
        log.info("阿里OSS图片上传成功,path={}", path);
        return path;
    }
}
