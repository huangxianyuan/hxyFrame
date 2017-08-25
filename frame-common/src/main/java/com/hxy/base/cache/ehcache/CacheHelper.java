package com.hxy.base.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheHelper extends UCacheManager
{
  protected static final Logger log = Logger.getLogger(CacheHelper.class);
  protected static Map<String, CacheHelper> helpers = new HashMap();
  protected Cache cache;

  private CacheHelper(String cacheName)
  {
    try
    {
      this.cache = manager.getCache(cacheName);
    } catch (Exception e) {
      log.error("Get cache(" + cacheName + ") instance fail: " + e.getMessage(), e);
    }
  }

  public static CacheHelper getCache(String cacheName) {
    CacheHelper ch = (CacheHelper)helpers.get(cacheName);
    if (ch == null) {
      ch = new CacheHelper(cacheName);
      helpers.put(cacheName, ch);
    }
    return ch;
  }

  public <T> T get(Object key)
  {
    Object s = null;
    if (this.cache != null) {
      try {
        Element elem = this.cache.get(key);
        if (elem != null)
          s = elem.getObjectValue();
      }
      catch (Exception e) {
        log.error("Get obj[" + key + "] from cache(" + this.cache.getName() + ") fail: " + e.getMessage(), e);
      }
    }
    return (T) s;
  }

  public boolean put(Object key, Object value)
  {
    if (this.cache != null) {
      try {
        this.cache.put(new Element(key, value));
        if (this.cache.getCacheConfiguration().isEternal()) {
          this.cache.flush();
        }
        return true;
      } catch (Exception e) {
        log.error("Put obj[" + key + "=" + value + "] to cache(" + this.cache.getName() + ") fail: " + e.getMessage(), e);
      }
    }
    return false;
  }

  public Cache getCache()
  {
    return this.cache;
  }

  public Element getElement(Object key)
  {
    if (this.cache != null) {
      try {
        return this.cache.get(key);
      } catch (Exception e) {
        log.error("Get element[" + key + "] from cache(" + this.cache.getName() + ") fail: " + e.getMessage(), e);
      }
    }
    return null;
  }

  public int size()
  {
    if (this.cache != null) {
      try {
        return this.cache.getSize();
      } catch (Exception e) {
        log.error("Get cache(" + this.cache.getName() + ") size fail: " + e.getMessage(), e);
      }
    }
    return 0;
  }

  public List getKeys() {
    if (this.cache != null) {
      try {
        return this.cache.getKeys();
      } catch (Exception e) {
        log.error("Get cache(" + this.cache.getName() + ") keys fail: " + e.getMessage(), e);
      }
    }
    return null;
  }

  public boolean remove(Object key)
  {
    if (this.cache != null) {
      try {
        if (this.cache.remove(key)) {
          if (this.cache.getCacheConfiguration().isEternal()) {
            this.cache.flush();
          }
          return true;
        }
        return false;
      } catch (Exception e) {
        log.error("Remove obj[" + key + "] from cache(" + this.cache.getName() + ") failed.", e);
      }
    }
    return false;
  }

  public boolean exists(Object key)
  {
    if (this.cache != null) {
      try {
        if (get(key) != null)
          return true;
      }
      catch (Exception e) {
        log.error("Check obj[" + key + "] is exists in cache(" + this.cache.getName() + ") failed.", e);
      }
    }
    return false;
  }

  public String getString(Object key) {
    return (String)get(key);
  }

  public Map getMap(Object key) {
    return (Map)get(key);
  }

  public <T> List<T> getList(Object key) {
    return (List)get(key);
  }

  public int getInt(Object key) {
    Object o = get(key);
    if (o == null) {
      return -1;
    }
    return ((Integer)o).intValue();
  }

  public short getShort(Object key) {
    Object o = get(key);
    if (o == null) {
      return -1;
    }
    return ((Short)o).shortValue();
  }

  public long getLong(Object key) {
    Object o = get(key);
    if (o == null) {
      return -1L;
    }
    return ((Long)o).longValue();
  }

  public float getFloat(Object key) {
    Object o = get(key);
    if (o == null) {
      return -1.0F;
    }
    return ((Float)o).floatValue();
  }

  public double getDouble(Object key) {
    Object o = get(key);
    if (o == null) {
      return -1.0D;
    }
    return ((Double)o).doubleValue();
  }

  public byte[] getBytes(Object key) {
    return (byte[])get(key);
  }

  public Byte getByte(Object key) {
    return (Byte)get(key);
  }

  public int[] getIntArray(Object key) {
    return (int[])get(key);
  }

  public <T> T[] getArray(Object key, Class<T> clazz)
  {
    return (T[])get(key);
  }
}