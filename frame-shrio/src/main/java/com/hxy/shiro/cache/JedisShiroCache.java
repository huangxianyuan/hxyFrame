package com.hxy.shiro.cache;

import com.yingxinhuitong.shiro.util.RedisManager;
import com.yingxinhuitong.shiro.util.SerializeUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by jack on 2016/12/13.
 */
public class JedisShiroCache<K, V> implements Cache<K, V> {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  private final String REDIS_SHIRO_CACHE = "shiro-cache:";

  private RedisManager cache;

  private String name;

  public JedisShiroCache(String name, RedisManager jedisManager) {
    this.name = name;
    this.cache = jedisManager;
  }

  @Override
  public V get(K key) throws CacheException {
    try {
      if (key == null) {
        return null;
      } else {
        byte[] rawValue = cache.get(getByteKey(key));
        @SuppressWarnings("unchecked")
        V value = (V) SerializeUtils.deserialize(rawValue);
        return value;
      }
    } catch (Throwable t) {
      throw new CacheException(t);
    }
  }

  @Override
  public V put(K key, V value) throws CacheException {
    try {
      cache.set(getByteKey(key), SerializeUtils.serialize(value));
      return value;
    } catch (Throwable t) {
      throw new CacheException(t);
    }
  }

  @Override
  public V remove(K key) throws CacheException {
    try {
      V previous = get(key);
      cache.del(getByteKey(key));
      return previous;
    } catch (Throwable t) {
      throw new CacheException(t);
    }
  }

  @Override
  public void clear() throws CacheException {
    try {
      String preKey = this.REDIS_SHIRO_CACHE + "*";
      byte[] keysPattern = preKey.getBytes();
      cache.del(keysPattern);
      cache.flushDB();
    } catch (Throwable t) {
      throw new CacheException(t);
    }
  }

  @Override
  public int size() {
    if (keys() == null)
      return 0;
    return keys().size();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Set<K> keys() {
    try {
      Set<byte[]> keys = cache.keys(this.REDIS_SHIRO_CACHE + "*");
      if (CollectionUtils.isEmpty(keys)) {
        return Collections.emptySet();
      } else {
        Set<K> newKeys = new HashSet<K>();
        for (byte[] key : keys) {
          newKeys.add((K) key);
        }
        return newKeys;
      }
    } catch (Throwable t) {
      throw new CacheException(t);
    }
  }

  @Override
  public Collection<V> values() {
    try {
      Set<byte[]> keys = cache.keys(this.REDIS_SHIRO_CACHE + "*");
      if (!CollectionUtils.isEmpty(keys)) {
        List<V> values = new ArrayList<V>(keys.size());
        for (byte[] key : keys) {
          @SuppressWarnings("unchecked")
          V value = get((K) key);
          if (value != null) {
            values.add(value);
          }
        }
        return Collections.unmodifiableList(values);
      } else {
        return Collections.emptyList();
      }
    } catch (Throwable t) {
      throw new CacheException(t);
    }
  }

  /**
   * 自定义relm中的授权/认证的类名加上授权/认证英文名字
   * @return
   */
  public String getName() {
    if (name == null)
      return "";
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * 获得byte[]型的key
   * @param key
   * @return
   */
  private byte[] getByteKey(K key) {
    if (key instanceof String) {
      String preKey = this.REDIS_SHIRO_CACHE + getName() + ":" + key;
      return preKey.getBytes();
    } else {
      return SerializeUtils.serialize(key);
    }
  }
}
