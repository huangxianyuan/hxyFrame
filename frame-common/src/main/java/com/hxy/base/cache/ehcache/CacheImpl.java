package com.hxy.base.cache.ehcache;


public abstract class CacheImpl implements ICache {
  public boolean put(Object key, Object value) {
    return getCacheHelper().put(key, value);
  }

  public Object get(Object key) {
    return getCacheHelper().get(key);
  }

  public <T> T get(Object key, Class<T> clazz) {
    return get(key, clazz);
  }

  public boolean exists(Object key) {
    return getCacheHelper().exists(key);
  }

  public boolean remove(Object key) {
    return getCacheHelper().remove(key);
  }
}