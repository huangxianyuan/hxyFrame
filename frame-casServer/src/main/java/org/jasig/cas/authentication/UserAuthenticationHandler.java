package org.jasig.cas.authentication;

import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;

/**
 * 自定义的用户登录认证类
 */
@Component(value="primaryAuthenticationHandler")
public class UserAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {
	@Resource
	private UserDaoJdbc userDaoJdbc;
	
	/**
	 * 认证用户名和密码是否正确
	 */
	@Override
	protected HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential transformedCredential) throws GeneralSecurityException, PreventedException {
		String username = transformedCredential.getUsername();
		String password = transformedCredential.getPassword();
		if(userDaoJdbc.verifyAccount(username, password)){
			return createHandlerResult(transformedCredential, new SimplePrincipal(username), null);
		}
		throw new FailedLoginException();
	}
}