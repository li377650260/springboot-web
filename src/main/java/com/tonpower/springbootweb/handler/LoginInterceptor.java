package com.tonpower.springbootweb.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonpower.springbootweb.domain.SysUser;
import com.tonpower.springbootweb.service.LoginService;
import com.tonpower.springbootweb.utils.UserThreadLocal;
import com.tonpower.springbootweb.vo.ErrorCode;
import com.tonpower.springbootweb.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/22 17:02
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*
        1. 判断请求接口路径是否为Controller方法
        2. 判断token是否为空，为空则是未登录
        3. 判断token不为空则校验token
        4. 认证成功放行
         */

        // 判断处理器是否为方法处理器,不是的话放行
        if (! (handler instanceof HandlerMethod)){
            return true;
        }

        String token = request.getHeader("Authorization");

        log.info("===============request start===============");
        log.info("request uri:{}",request.getRequestURI());
        log.info("request method:{}",request.getMethod());
        log.info("token:{}"+token);
        log.info("================request end================");
        // 判断token是否为空，为空则是未登录
        if (StringUtils.isEmpty(token) || "undefined".equals(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(result);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(json);
            return false;
        }
        // 判断token是否关联了用户信息
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(result);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(json);
        }
        // 登录验证成功，将用户信息放入线程变量中
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 线程变量使用完用户信息后需要进行删除，否则存储太多会有内存泄露的风险
        UserThreadLocal.remove();
    }
}
