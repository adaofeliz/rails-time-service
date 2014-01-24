package com.adaofeliz.railstime.interceptor;

import com.adaofeliz.railstime.context.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.TimeZone;

/**
 * User: ADAO
 * Date: 5/2/13 - 6:54 AM
 */
public class UserContextInterceptor extends HandlerInterceptorAdapter {

    private static Logger LOG = LoggerFactory.getLogger(UserContextInterceptor.class);

    @Resource
    private UserContext userContext;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // TODO - Implement GeoIP
        userContext.setUserLocale(Locale.UK);
        userContext.setUserTimezone(TimeZone.getTimeZone("Europe/London"));

        LOG.debug(userContext.toString());

        return true;
    }

}
