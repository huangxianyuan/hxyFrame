package com.hxy.base.cache;


import com.hxy.base.cache.ehcache.CacheHelper;
import com.hxy.base.common.Constant;

/**
 * 用户缓存
 */
public class CodeCache {
	private static CacheHelper helper = null;

	private static CacheHelper getCacheHelper() {
		if (helper == null) {
			helper = CacheHelper.getCache(Constant.CODE_CACHE);
		}
		return helper;
	}

	public static boolean exists(Object key) {
		return getCacheHelper().exists(key);
	}

	public static <T> T get(Object key) {
		return getCacheHelper().get(key);
	}

	public static boolean put(Object key, Object value) {
		return getCacheHelper().put(key, value);
	}

	public static boolean remove(Object key) {
		return getCacheHelper().remove(key);
	}



}
