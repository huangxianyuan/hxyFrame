package com.hxy.shiro.redisSession;

import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by jack on 2016/12/13.
 */
public interface ShiroSessionRepository {

  void saveSession(Session session);

  void deleteSession(Serializable sessionId);

  Session getSession(Serializable sessionId);

  Collection<Session> getAllSessions();
}
