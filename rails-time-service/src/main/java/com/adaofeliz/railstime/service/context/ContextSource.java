package com.adaofeliz.railstime.service.context;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

/**
 * User: ADAO
 * Date: 6/14/13 - 6:58 PM
 */

@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Scope(value = SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public @interface ContextSource {

    /**
     * The name to be used when adding the ContextSource to the ConfigEvaluationContext. If no
     * value is specified, it falls back to using the bean name.
     */
    String value() default "";
}
