package com.happymail.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil
{
	public static String DEFAULT_DOMAIN=Config.CookieConfig.DEFAULT_DOMAIN;
	public static Integer DEFAULT_MAX_AGE=Config.CookieConfig.DEAULT_MAX_AGE;
	public static String DEFAULT_PATH=Config.CookieConfig.DEAULT_PATH;
	/**
	 * 设置Cookie
	 * @param response
	 * @param cookieName
	 * @param value
	 * @param maxAge 最大过期时间
	 * @return
	 */
	
	public static Cookie addCookie(HttpServletResponse response,String cookieName,String value,Integer maxAge) {  
        Cookie cookie = new Cookie(cookieName,value); 
        cookie.setMaxAge(maxAge);
        cookie.setDomain(DEFAULT_DOMAIN);
        cookie.setPath(DEFAULT_PATH);
        response.addCookie(cookie);
        return cookie;  
    }
	/**
	 * 设置cookie 均使用默认值
	 * @param response
	 * @param cookieName
	 * @param value
	 * @return
	 */
	public static Cookie addCookie(HttpServletResponse response,String cookieName,String value) {  
        Cookie cookie = new Cookie(cookieName,value); 
        cookie.setMaxAge(DEFAULT_MAX_AGE);
        cookie.setDomain(DEFAULT_DOMAIN);
        cookie.setPath(DEFAULT_PATH);
        response.addCookie(cookie);
        return cookie;  
    }
    /**
     *  获取 cookie  
     */
    public static String getCookie(HttpServletRequest request, String cookieName) {  
        Cookie[] cookies = request.getCookies();  
        if (cookies != null) {  
            for (Cookie cookie : cookies) {  
                if (cookieName.equalsIgnoreCase(cookie.getName())) {  
                    return cookie.getValue();  
                }  
            }  
        }  
        return null;
    }
    /**
     *  删除 cookie  
     * @param response
     * @param cookieName
     */
    public static void delCookie(HttpServletResponse response, String cookieName) {  
    	Cookie cookie = new Cookie(cookieName,null); 
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
