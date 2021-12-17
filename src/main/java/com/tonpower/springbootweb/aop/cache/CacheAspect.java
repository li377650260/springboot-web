package com.tonpower.springbootweb.aop.cache;

import com.alibaba.fastjson.JSON;
import com.tonpower.springbootweb.aop.comment.LogAnnotation;
import com.tonpower.springbootweb.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/29 22:40
 */

@Aspect
@Component
@Slf4j
public class CacheAspect {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Pointcut("@annotation(com.tonpower.springbootweb.aop.cache.Cache)")
    public void pointcut(){};

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point){
        try {
            long beginTime = System.currentTimeMillis();
            Signature signature = point.getSignature();
            //类名
            String className = point.getTarget().getClass().getSimpleName();
            //调用的方法名
            String methodName = signature.getName();


            Class[] parameterTypes = new Class[point.getArgs().length];
            Object[] args = point.getArgs();
            //参数
            String params = "";
            for(int i=0; i<args.length; i++) {
                if(args[i] != null) {
                    params += JSON.toJSONString(args[i]);
                    parameterTypes[i] = args[i].getClass();
                }else {
                    parameterTypes[i] = null;
                }
            }
            if (!StringUtils.isEmpty(params)) {
                //加密 以防出现key过长以及字符转义获取不到的情况
                params = DigestUtils.md5Hex(params);
            }
            Method method = point.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            //获取Cache注解
            Cache annotation = method.getAnnotation(Cache.class);
            //缓存过期时间
            long expire = annotation.expire();
            //缓存名称
            String name = annotation.name();
            //先从redis获取
            String redisKey = name + "::" + className+"::"+methodName+"::"+params;
            String redisValue = redisTemplate.opsForValue().get(redisKey);
            if (!StringUtils.isEmpty(redisValue)){
                log.info("走了缓存,{},{}",className,methodName);
                long time = System.currentTimeMillis() - beginTime;
                log.info("走缓存的时间:{}ms"+time);
                return JSON.parseObject(redisValue, Result.class);
            }
            Object proceed = point.proceed();
            redisTemplate.opsForValue().set(redisKey,JSON.toJSONString(proceed), Duration.ofMillis(expire));
            log.info("存入缓存~~~ {},{}",className,methodName);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Result.fail(-999,"缓存系统错误");
    }
}

