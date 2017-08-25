package com.hxy.base.cache.ehcache;

public interface ICache {
  public abstract CacheHelper getCacheHelper();

  public abstract boolean put(Object paramObject1, Object paramObject2);

  public abstract Object get(Object paramObject);

  public abstract <T> T get(Object paramObject, Class<T> paramClass);

  public abstract boolean exists(Object paramObject);

  public abstract boolean remove(Object paramObject);
}
