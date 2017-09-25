package com.hxy.shiro.redisSession;


/**
 * 由于SimpleSession lastAccessTime更改后也会调用SessionDao update方法，
 * 增加标识位，如果只是更新lastAccessTime SessionDao update方法直接返回
 *
 * Session Attribute
 * DefaultSubjectContext.PRINCIPALS_SESSION_KEY 保存 principal
 * DefaultSubjectContext.AUTHENTICATED_SESSION_KEY 保存 boolean是否登陆
 * @see org.apache.shiro.subject.support.DefaultSubjectContext
 */
public class ShiroSession extends AbstractSession {

    public ShiroSession() {
        super();
        this.setChanged(true);
    }

    public ShiroSession(String host) {
        super(host);
        this.setChanged(true);
    }

}
