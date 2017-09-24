package com.hxy.shiro.redisSession;

import com.hxy.base.common.Constant;
import com.hxy.sentinelRedis.RedisUtil;
import org.apache.shiro.session.Session;
import org.apache.zookeeper.server.util.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jack on 2016/12/13.
 */
public class JedisShiroSessionRepository implements ShiroSessionRepository {
  private static Logger logger = LoggerFactory.getLogger(JedisShiroSessionRepository.class);

  @Override
  public void saveSession(Session session) {
    if (session == null || session.getId() == null) {
      logger.error("session or session ID is null");
    }
    String key = Constant.REDIS_SHIRO_SESSION+session.getId();
    Long timeOut = session.getTimeout() / 1000;
    try {
      RedisUtil.setObject(key, session, timeOut.intValue());
    } catch (Exception e) {
      logger.error("保存sessionId："+key+"失败!");
      e.printStackTrace();
    }
  }

  @Override
  public void deleteSession(Serializable sessionId) {
    if (sessionId == null) {
      logger.error("session id is null");
      return;
    }
    String key = Constant.REDIS_SHIRO_SESSION+sessionId;
    try {
      RedisUtil.del(key);
    } catch (Exception e) {
      logger.error("删除sessionId："+key+"失败!");
      e.printStackTrace();
    }
  }

  @Override
  public Session getSession(Serializable sessionId) {
    if (null == sessionId) {
      logger.error("session id is null");
      return null;
    }
    String key = Constant.REDIS_SHIRO_SESSION+sessionId;
    Session session= null;
    try {
      session = (Session) RedisUtil.getObject(key);
    } catch (Exception e) {
      logger.error("获取sessionId："+key+"失败!");
      e.printStackTrace();
    }

    return session;
  }

  @Override
  public Collection<Session> getAllSessions() {
    RedisUtil
    return sessions;
  }

  private byte[] getByteKey(Serializable sessionId) {
    String preKey = this.REDIS_SHIRO_SESSION + sessionId;
    return preKey.getBytes();
  }

}
