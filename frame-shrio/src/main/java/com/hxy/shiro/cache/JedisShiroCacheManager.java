package com.hxy.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jack on 2016/12/13.
 */
public class JedisShiroCacheManager implements ShiroCacheManager {
  private static final Logger logger = LoggerFactory.getLogger(JedisShiroCacheManager.class);

  private RedisManager redisManager;

  // fast lookup by name map
  private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

  @Override
  public <k, v> Cache<k, v> getCache(String name) {
    logger.debug("get instance of RedisCache,name: " + name);

    Cache c = caches.get(name);

    if (c == null) {

      // initialize the Redis manager instance
      redisManager.init();

      // create a new cache instance
      c = new JedisShiroCache<k, v>(name, redisManager);

      // add it to the cache collection
      caches.put(name, c);
    }
    return c;
  }

  @Override
  public void destroy() {
    redisManager.init();
    redisManager.flushDB();
  }

  public RedisManager getRedisManager() {
    return redisManager;
  }

  public void setRedisManager(RedisManager redisManager) {
    this.redisManager = redisManager;
  }

}
