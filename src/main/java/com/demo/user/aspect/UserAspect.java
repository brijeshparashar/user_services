package com.demo.user.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
/*This class is used to induce Entering and Exiting logs on methods */
public class UserAspect {

    @Pointcut("execution(* com.demo.user.*.*.*(..))")
    private void userService() {
        // empty
    }

    @Around("userService()")
    public Object logMethodStartAndEnd(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed;
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        log.debug("Execution of Method {} of class {} starts", methodName, className);
        try {
            proceed = joinPoint.proceed();
        } catch (Exception ex) {
            log.debug("Error occurred while executing method {} of class {}", methodName, className);
            throw ex;
        }
        log.debug("Execution of Method {} of class {} ends", methodName, className);
        return proceed;
    }
}
