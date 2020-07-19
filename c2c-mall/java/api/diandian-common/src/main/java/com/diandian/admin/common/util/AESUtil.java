package com.diandian.admin.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 字符加密工具类
 *
 * @author pusc
 * @since 20101031
 */
public class AESUtil {
	// 加密算法
	private static final String AES = "AES";
	// 密钥
	private static final String CRYPT_KEY = "b7c5bfc1704e93df";

	/**
	 * 加密字节数组
	 *
	 * @param srcStr
	 *            待加密的字节数组
	 * @param key
	 *            密钥
	 *
	 * @return 返回加密后的字节数组
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] srcStr, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(AES);
		SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), AES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey); // 设置密钥和加密形式
		return cipher.doFinal(srcStr);
	}

	/**
	 * 解密字节数组
	 *
	 * @param encryptStr
	 *            待解密的字节数组
	 * @param key
	 *            密钥
	 *
	 * @return 返回解密后的字节数组
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] encryptStr, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(AES);
		SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), AES);// 设置加密Key
		cipher.init(Cipher.DECRYPT_MODE, securekey);// 设置密钥和解密形式
		return cipher.doFinal(encryptStr);
	}

	/**
	 * 二行制转十六进制字符串
	 *
	 * @param b
	 *            2进制byte数组
	 *
	 * @return 16进制字符串
	 */
	private static String byte2hex(byte[] b) {

		StringBuffer sb = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				sb.append("0" + stmp);
			else
				sb.append(stmp);
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 十六进制转二行制
	 *
	 * @param b
	 *            字节数组
	 *
	 * @return 字节数组
	 */
	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("SecretUtil.hex2byte() length is not even number!");
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
	 * @param data
	 *            待解密的字符串
	 *
	 * @return
	 * @throws Exception
	 */
	public final static String decrypt(String data, String key) {
		try {
			if (key == null || key.trim().equals(""))
				key = CRYPT_KEY;
			return new String(decrypt(hex2byte(data.getBytes("UTF-8")), key), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密
	 *
	 * @param data
	 *            待加密的字符串
	 * @return
	 * @throws Exception
	 */
	public final static String encrypt(String data, String key) {
		try {
			if (key == null || key.trim().equals(""))
				key = CRYPT_KEY;
			return byte2hex(encrypt(data.getBytes("UTF-8"), key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String content = "H1234567,233,;233,12";
		content = "Hello World of Encryption using AES ";
		String encode = encrypt(content, null);
		String decode = decrypt(encode, null);
		System.out.println(encode);
		System.out.println(decode);
	}

}
