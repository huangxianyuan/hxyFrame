package org.jasig.cas.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 类ShiroUtils的功能描述:
 * Shiro工具类
 * @auther hxy
 * @date 2017-08-25 16:19:35
 */
public class ShiroUtils {

	/**  加密算法 */
	public final static String algorithmName = "SHA-256";
	/**
	 * 加密散列次数
	 */
	public static final int hashIterations= 1;

	public static String EncodeSalt(String password, String salt) {
		return new SimpleHash(algorithmName, password, salt, hashIterations).toString();
	}

}
