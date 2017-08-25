package com.hxy.base.cache.ehcache;

import java.util.List;
import java.util.Map;

public class CacheUtils
{
  private static CacheHelper cache;

  private static CacheHelper getCache()
  {
    if (cache == null) {
      cache = CacheHelper.getCache("sysCache");
    }
    return cache;
  }

  public static boolean put(Object key, Object value) {
    return getCache().put(key, value);
  }

  public static <T> T get(Object key) {
    return getCache().get(key);
  }

  public static String getString(Object key) {
    return (String)getCache().get(key);
  }

  public static Map getMap(Object key) {
    return (Map)getCache().get(key);
  }

  public static <T> List<T> getList(Object key) {
    return (List)getCache().get(key);
  }

  public static int getInt(Object key) {
    Object o = getCache().get(key);
    if (o == null) {
      return -1;
    }
    return ((Integer)o).intValue();
  }

  public static short getShort(Object key) {
    Object o = getCache().get(key);
    if (o == null) {
      return -1;
    }
    return ((Short)o).shortValue();
  }

  public static long getLong(Object key) {
    Object o = getCache().get(key);
    if (o == null) {
      return -1L;
    }
    return ((Long)o).longValue();
  }

  public static float getFloat(Object key) {
    Object o = getCache().get(key);
    if (o == null) {
      return -1.0F;
    }
    return ((Float)o).floatValue();
  }

  public static double getDouble(Object key) {
    Object o = getCache().get(key);
    if (o == null) {
      return -1.0D;
    }
    return ((Double)o).doubleValue();
  }

  public static byte[] getBytes(Object key) {
    return (byte[])getCache().get(key);
  }

  public static Byte getByte(Object key) {
    return (Byte)getCache().get(key);
  }

  public static int[] getIntArray(Object key) {
    return (int[])getCache().get(key);
  }

  public static <T> T[] getArray(Object key, Class<T> clazz) {
    return (T[])getCache().get(key);
  }

  public static boolean exists(Object key) {
    return getCache().exists(key);
  }

  public static boolean remove(String key) {
    return getCache().remove(key);
  }

  public static List getKeys() {
    return getCache().getKeys();
  }

}