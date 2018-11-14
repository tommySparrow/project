/**
 * 
 */
package com.house.apigateway.interceptor;

import com.google.common.base.Joiner;
import com.house.apigateway.bean.User;
import com.house.apigateway.common.CommonConstants;
import com.house.apigateway.context.UserContext;
import com.house.apigateway.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
  
  private static final String TOKEN_COOKIE = "token";
  
  
  @Autowired
  private UserMapper userMapper;

  
  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
          throws Exception {
    Map<String, String[]> map = req.getParameterMap();
    map.forEach((k,v) ->req.setAttribute(k, Joiner.on(",").join(v)));
    String requestURI = req.getRequestURI();
    if (requestURI.startsWith("/static") || requestURI.startsWith("/error")) {//放行的资源路径
      return true;
    }
      Cookie cookie = WebUtils.getCookie(req, TOKEN_COOKIE);//获取包含"token"值得cookie对象
    if (cookie != null && StringUtils.isNoneBlank(cookie.getValue())) {
        User user = userMapper.getUserByToken(cookie.getValue());//根据token值,获取user对象
        if (user != null) {
          req.setAttribute(CommonConstants.LOGIN_USER_ATTRIBUTE, user);//封装loginUser到req中
          UserContext.setUser(user);
        }
    }
    return true;
  }
  

  @Override
  public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler,
                         ModelAndView modelAndView) throws Exception {
    String requestURI = req.getRequestURI();
    if (requestURI.startsWith("/static") || requestURI.startsWith("/error")) {
      return ;
    }
    User user = UserContext.getUser();
    if (user != null && StringUtils.isNoneBlank(user.getToken())) {
       String token = requestURI.startsWith("logout")? "" : user.getToken();//获取token
       Cookie cookie = new Cookie(TOKEN_COOKIE, token);//将token设置进cookie中
       cookie.setPath("/");
       cookie.setHttpOnly(false);
       res.addCookie(cookie);//返回cookie
    }
    
  }
  
  

  @Override
  public void afterCompletion(HttpServletRequest req, HttpServletResponse response, Object handler, Exception ex)
          throws Exception {
    UserContext.remove();
  }
}
