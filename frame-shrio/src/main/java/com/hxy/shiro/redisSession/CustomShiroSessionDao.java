package com.hxy.shiro.redisSession;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by jack on 2016/12/13.
 */
public class CustomShiroSessionDao extends AbstractSessionDAO {
  private static Logger logger = LoggerFactory.getLogger(CustomShiroSessionDao.class);
  private ShiroSessionRepository shiroSessionRepository;

  public ShiroSessionRepository getShiroSessionRepository() {
    return shiroSessionRepository;
  }

  public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
    this.shiroSessionRepository = shiroSessionRepository;
  }

  @Override
  public void delete(Session session) {
    if (session == null) {
      logger.error("session or session id is null");
      return;
    }
    Serializable id = session.getId();
    if (id != null)
      getShiroSessionRepository().deleteSession(id);
  }

  @Override
  public Collection<Session> getActiveSessions() {
    return getShiroSessionRepository().getAllSessions();
  }

  @Override
  public void update(Session session) throws UnknownSessionException {
    getShiroSessionRepository().saveSession(session);
  }

  @Override
  protected Serializable doCreate(Session session) {
    Serializable sessionId = this.generateSessionId(session);
    this.assignSessionId(session, sessionId);
    getShiroSessionRepository().saveSession(session);
    return sessionId;
  }

  @Override
  protected Session doReadSession(Serializable sessionId) {
    return getShiroSessionRepository().getSession(sessionId);
  }

}
