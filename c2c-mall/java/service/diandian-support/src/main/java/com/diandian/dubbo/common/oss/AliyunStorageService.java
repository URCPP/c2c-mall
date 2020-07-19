package com.diandian.dubbo.common.oss;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.diandian.dubbo.common.exception.StorageException;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 阿里OSS文件存储服务
 * @author zzhihong
 * @date 2018-09-20
 */
@Slf4j
public class AliyunStorageService implements AutoCloseable {

    private AliyunStorageProperties properties;
    private OSSClient client;

    public AliyunStorageService(AliyunStorageProperties properties) {
        this.properties = properties;
        init();
    }

    /**
     * ossClient 初始化
     */
    private void init() {
        this.client = new OSSClient(properties.getEndPoint(),
                properties.getAccessKeyId(), properties.getAccessKeySecret());
    }

    /**
     * 文件流上传 指定文件路径
     *
     * @param inputStream
     * @param path
     * @return
     */
    public String upload(InputStream inputStream, String path) {
        try {
            client.putObject(properties.getBucketName(), path, inputStream);
        } catch (Exception e) {
            log.error("阿里云上传文件失败", e);
            throw new StorageException("上传文件失败，请检查配置信息", e);
        }
        return "/" + path;
    }

    /**
     * 字节数组上传 指定文件路径
     *
     * @param data
     * @param path
     * @return
     */
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    /**
     * 字节数组上传 指定文件后缀
     *
     * @param data
     * @param suffix
     * @return
     */
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(properties.getPrefix(), suffix));
    }

    /**
     * 文件流上传 指定文件后缀
     *
     * @param inputStream
     * @param suffix
     * @return
     */
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(properties.getPrefix(), suffix));
    }

    /**
     * 上传base64位图片 必须 data:image/xxx;base64, 开头
     *
     * @param prefix
     * @param base64Str
     * @return
     */
    public String uploadBase64Img(String prefix, String base64Str) {
        String result = null;
        try {
            //data:image/png;base64,
            SerialBlob serialBlob = decodeToImage(base64Str.substring(base64Str.indexOf(",") + 1));
            int i = base64Str.indexOf("image/");
            String suffix = "." + base64Str.substring(i + 6, base64Str.indexOf(";"));
            InputStream binaryStream = serialBlob.getBinaryStream();
            result = upload(binaryStream, getPath(prefix, suffix));
        } catch (Exception e) {
            log.error("图片上传失败", e);
            throw new StorageException("图片上传失败");
        }
        return result;
    }
    public String uploadInputStream(String prefix,InputStream inputStream,String suffix){
        String result;
        try {
            result = upload(inputStream, getPath(prefix, suffix));
        } catch (Exception e) {
            log.error("图片上传失败", e);
            throw new StorageException("图片上传失败");
        }
        return result;
    }

    /**
     * 从oss获取文件对象
     *
     * @param key
     * @return
     */
    public InputStream getObject(String key) {
        key = key.replace(properties.getFileEndPoint() + "/", "");
        OSSObject object = client.getObject(properties.getBucketName(), key);
        return object.getObjectContent();
    }


    /**
     * 文件路径
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    private String getPath(String prefix, String suffix) {
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //文件路径
        Date currrentDate = new Date();
        String path = DateUtil.year(currrentDate) + "/" + (DateUtil.month(currrentDate) + 1)
                + "/" + DateUtil.dayOfMonth(currrentDate) + "/" + uuid;
        if (StrUtil.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }
        return path + suffix;
    }

    /**
     * base64图片转换
     *
     * @param imageString
     * @return
     * @throws Exception
     */
    private SerialBlob decodeToImage(String imageString) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageByte = decoder.decodeBuffer(imageString);
        return new SerialBlob(imageByte);
    }

    @Override
    public void close() throws Exception {
        client.shutdown();
    }
}
