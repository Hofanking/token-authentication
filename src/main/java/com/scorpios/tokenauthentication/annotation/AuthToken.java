package com.scorpios.tokenauthentication.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Think
 * @Title: AuthToken
 * @ProjectName token-authentication
 * @Description: TODO
 * @date 2019/1/1815:52
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthToken {

}