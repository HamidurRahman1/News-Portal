package com.hamidur.ss.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LoggingAspect
{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution (* com.hamidur.ss.rest.RestrictedRESTController..*(..)))")
    public void before(JoinPoint joinPoint)
    {
        logger.info(joinPoint.getSignature().getName() + "() is executed from -> " + joinPoint.getSignature().getDeclaringType());
    }
}
