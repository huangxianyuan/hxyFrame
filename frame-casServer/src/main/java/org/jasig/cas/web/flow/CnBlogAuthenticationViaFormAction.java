package org.jasig.cas.web.flow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.UsernamePasswordCaptchaCredential;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.util.StringUtils;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class CnBlogAuthenticationViaFormAction extends AuthenticationViaFormAction{

    public final Event validatorCaptcha(RequestContext context, Credential credential, MessageContext messageContext) throws Exception {
    	
    		final HttpServletRequest request = WebUtils.getHttpServletRequest(context);  
    		HttpSession session = request.getSession();  
    		String captcha = (String)session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);  
    		session.removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);  
    		
    		UsernamePasswordCaptchaCredential upc = (UsernamePasswordCaptchaCredential)credential;  
    		String submitAuthcodeCaptcha =upc.getCaptcha(); 
    		
    		
    		if(!StringUtils.hasText(submitAuthcodeCaptcha) || !StringUtils.hasText(submitAuthcodeCaptcha)){
                messageContext.addMessage((new MessageBuilder()).error().code("required.captcha").build());
				return this.newEvent(ERROR);
    		}
    		if(submitAuthcodeCaptcha.equals(captcha)){
                return this.newEvent(SUCCESS);
    		}
            messageContext.addMessage((new MessageBuilder()).error().code("error.authentication.captcha.bad").build());
            return this.newEvent(ERROR);
    }

    private Event newEvent(String id) {
        return new Event(this, id);
    }
}

