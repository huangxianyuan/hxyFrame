package com.hxy.shiro.cache;

import org.apache.shiro.cache.Cache;

/**
 * Created by jack on 2016/12/13.
 */
public interface ShiroCacheManager {
  <k, v> Cache<k, v> getCache(String name);

  void destroy();
}

