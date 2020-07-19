package com.diandian.dubbo.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 字符加密工具类
 *
 * @author pusc
 * @since 20101031
 */
@Slf4j
public class AESUtil {

    /**
     * 加密算法
     */
    private static final String AES = "AES";
    /**
     * 密钥
     */
    private static final String CRYPT_KEY = "b7c5bfc1704e93df";
    /**
     * charset
     */
    private static final String CHARSET = "UTF-8";

    private AESUtil() {
    }

    /**
     * 加密字节数组
     *
     * @param srcStr 待加密的字节数组
     * @param key    密钥
     * @return 返回加密后的字节数组
     * @throws Exception
     */
    private static byte[] encrypt(byte[] srcStr, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(AES);
        SecretKeySpec secureKey = new SecretKeySpec(key.getBytes(), AES);
        // 设置密钥和加密形式
        cipher.init(Cipher.ENCRYPT_MODE, secureKey);
        return cipher.doFinal(srcStr);
    }

    /**
     * 解密字节数组
     *
     * @param encryptStr 待解密的字节数组
     * @param key        密钥
     * @return 返回解密后的字节数组
     * @throws Exception
     */
    private static byte[] decrypt(byte[] encryptStr, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(AES);
        // 设置加密Key
        SecretKeySpec secureKey = new SecretKeySpec(key.getBytes(), AES);
        // 设置密钥和解密形式
        cipher.init(Cipher.DECRYPT_MODE, secureKey);
        return cipher.doFinal(encryptStr);
    }

    /**
     * 二行制转十六进制字符串
     *
     * @param b 2进制byte数组
     * @return 16进制字符串
     */
    private static String byte2hex(byte[] b) {

        StringBuilder sb = new StringBuilder();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 十六进制转二行制
     *
     * @param b 字节数组
     * @return 字节数组
     */
    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0) {
            throw new IllegalArgumentException("SecretUtil.hex2byte() length is not even number!");
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * 解密
     *
     * @param data 待解密的字符串
     * @return
     * @throws Exception
     */
    public final static String decrypt(String data, String key) {
        try {
            if (key == null || "".equals(key.trim())) {
                key = CRYPT_KEY;
            }
            return new String(decrypt(hex2byte(data.getBytes(AESUtil.CHARSET)), key), AESUtil.CHARSET);
        } catch (Exception e) {
            log.error("AES解密异常", e);
        }
        return null;
    }

    /**
     * 加密
     *
     * @param data 待加密的字符串
     * @return
     * @throws Exception
     */
    public final static String encrypt(String data, String key) {
        try {
            if (key == null || "".equals(key.trim())) {
                key = CRYPT_KEY;
            }
            return byte2hex(encrypt(data.getBytes(AESUtil.CHARSET), key));
        } catch (Exception e) {
            log.error("AES加密异常", e);
        }
        return null;
    }
}
