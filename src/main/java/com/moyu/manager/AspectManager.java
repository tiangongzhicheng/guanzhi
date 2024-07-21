package com.moyu.manager;


import com.alibaba.fastjson.JSONObject;
import com.moyu.annotation.AuditOperate;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Aspect
@Service
public class AspectManager {

    @Pointcut("@annotation(com.moyu.annotation.AuditOperate)")
    public void aopPointCut(){

    }

    @Around("aopPointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        log.info("args==={}", JSONObject.toJSON(args));
        log.info("args==={}",args[0] );
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        AuditOperate declaredAnnotation = method.getDeclaredAnnotation(AuditOperate.class);
        String name = declaredAnnotation.name();
        String value = declaredAnnotation.value();
        log.info("name==={}", name);
        log.info("value==={}", value);
        try {
            Object proceed = joinPoint.proceed(args);
            return proceed;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }finally {
            log.info("finally====over");
        }

    }
}
