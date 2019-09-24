package com.example.springbootutils.common.aop;

import com.example.springbootutils.common.exception.ResultException;
import com.example.springbootutils.common.exception.ReturnInfoEnum;
import com.example.springbootutils.utils.IPUtils;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * guava RateLimiter令牌桶限流
 */
@Aspect
@Configuration
public class LimitAspect {

    private static Logger logger = LoggerFactory.getLogger(LimitAspect.class);

    /**
     * 根据IP分不同的令牌桶, 每天自动清理缓存
     */
    private static LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key) {
                    // 新的IP初始化 每秒只发出5个令牌
                    System.out.println("开始初始化，key："+key);
                    return RateLimiter.create(1);
                }
            });

    /**
     * Service层切点  限流
     */
    @Pointcut("@annotation(com.example.springbootutils.common.aop.ServiceLimit)")
    public void ServiceAspect() {
    }

    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) throws ResultException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ServiceLimit limitAnnotation = method.getAnnotation(ServiceLimit.class);
        ServiceLimit.LimitType limitType = limitAnnotation.limitType();
        String key = limitAnnotation.key();
        Object obj;
        try {
            if(limitType.equals(ServiceLimit.LimitType.IP)){
                key = IPUtils.getIpAddr();
            }
            RateLimiter rateLimiter = caches.get(key);
            logger.info("用户id：{}",key);
            Boolean flag = rateLimiter.tryAcquire();
            if(flag){
                obj = joinPoint.proceed();
            }else{
                throw new ResultException(ReturnInfoEnum.businessError.code(),"手速太快了");
            }
        } catch (Throwable e) {
            throw new ResultException(ReturnInfoEnum.businessError.code(),"手速太快了");
        }
        return obj;
    }



}
