package com.hxy.shiro.filter;

import org.apache.log4j.Logger;
import org.jasig.cas.client.session.HashMapBackedSessionMappingStorage;
import org.jasig.cas.client.session.SessionMappingStorage;
import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 类SingleSignOutFilter的功能描述:
 * 
 * @auther hxy
 * @date 2017-09-08 11:38:08
 */
public class SingleSignOutFilter extends AbstractConfigurationFilter {

    private String artifactParameterName;
    private static SessionMappingStorage SESSION_MAPPING_STORAGE = new HashMapBackedSessionMappingStorage();
    private static Logger log = Logger.getLogger(SingleSignOutFilter.class);

    public SingleSignOutFilter() {
        this.artifactParameterName = "ticket";
    }

    public void init(FilterConfig filterConfig)
            throws ServletException {
        setArtifactParameterName(getPropertyFromInitParams(filterConfig, "artifactParameterName", "ticket"));
        init();
    }

    public void init() {
        CommonUtils.assertNotNull(this.artifactParameterName, "artifactParameterName cannot be null.");
        CommonUtils.assertNotNull(SESSION_MAPPING_STORAGE, "sessionMappingStorage cannote be null.");
    }

    public void setArtifactParameterName(String artifactParameterName) {
        this.artifactParameterName = artifactParameterName;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String logoutRequest = CommonUtils.safeGetParameter(request, "logoutRequest");
        Enumeration ff = request.getParameterNames();
        String a = request.getQueryString();
        if (CommonUtils.isNotBlank(logoutRequest)) {
            final String sessionIdentifier = XmlUtils.getTextForElement(logoutRequest, "SessionIndex");

            if (CommonUtils.isNotBlank(sessionIdentifier)) {
                final HttpSession session = SESSION_MAPPING_STORAGE.removeSessionByMappingId(sessionIdentifier);

                if (session != null) {
                    String sessionID = session.getId();
                    try {
                        session.invalidate();
                    } catch (final IllegalStateException e) {

                    }
                }
            }
        } else {
            final String artifact = CommonUtils.safeGetParameter(request, this.artifactParameterName);
            final HttpSession session = request.getSession(false);

            if (CommonUtils.isNotBlank(artifact) && session != null) {
                try {
                    SESSION_MAPPING_STORAGE.removeBySessionById(session.getId());
                } catch (final Exception e) {

                }
                SESSION_MAPPING_STORAGE.addSessionById(artifact, session);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void setSessionMappingStorage(SessionMappingStorage storage) {
        SESSION_MAPPING_STORAGE = storage;
    }

    public static SessionMappingStorage getSessionMappingStorage() {
        return SESSION_MAPPING_STORAGE;
    }

    public void destroy() {
    }
}
