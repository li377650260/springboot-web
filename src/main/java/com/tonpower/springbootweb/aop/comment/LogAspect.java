package com.tonpower.springbootweb.aop.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonpower.springbootweb.utils.HttpContextUtils;
import com.tonpower.springbootweb.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/27 20:34
 */

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.tonpower.springbootweb.aop.comment.LogAnnotation)")
    public void pt(){};

    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long time = System.currentTimeMillis() - beginTime;

        recordLog(joinPoint,time);
        return proceed;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) throws JsonProcessingException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("========log start==========");
        log.info(logAnnotation.model());
        log.info(logAnnotation.operator());

        Object[] args = joinPoint.getArgs();
        ObjectMapper om = new ObjectMapper();
        String param = om.writeValueAsString(args);
        log.info("param:{}"+param);
        HttpServletRequest httpServletRequest = HttpContextUtils.get();
        log.info("ip:{}"+ IpUtils.getIpAddr(httpServletRequest));

        log.info("time:{} ms"+time);
        log.info("============log end=============");
    }

}
