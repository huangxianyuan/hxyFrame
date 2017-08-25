package com.hxy.base.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;

public class UCacheManager
{
  static final Log log = LogFactory.getLog(UCacheManager.class);
  public static CacheManager manager;
  protected Cache cache;

  public UCacheManager()
  {
    try
    {
      manager = CacheManager.getInstance();
      if (manager == null) {
        manager = CacheManager.create();
        log.info("Initialize cache manager: " + manager);
      }
    } catch (CacheException e) {
      log.error("Initialize cache manager failed.", e);
    }
  }

  public UCacheManager(String configFile) {
    try {
      manager = CacheManager.getInstance();
      if (manager == null) {
        manager = CacheManager.create(configFile);
        log.info("Initialize cache manager: " + manager);
      }
    } catch (CacheException e) {
      log.error("Initialize cache manager failed.", e);
    }
  }

  public UCacheManager(InputStream is) {
    try {
      manager = CacheManager.getInstance();
      if (manager == null) {
        manager = CacheManager.create(is);
        log.info("Initialize cache manager: " + manager);
      }
    } catch (CacheException e) {
      log.error("Initialize cache manager failed.", e);
      try
      {
        is.close(); } catch (Exception localException) {  } } finally { try { is.close(); }
      catch (Exception localException1)
      {
      }
    }
  }

  public static void shutdown()
  {
    if (manager != null) {
      log.info("Cache manager shutdown!!!");
      try {
        manager.shutdown();
      } catch (CacheException e) {
        log.error("Cache manager shutdown failed.", e);
      }
    }
  }
}