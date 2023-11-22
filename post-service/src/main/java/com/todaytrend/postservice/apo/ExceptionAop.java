package com.todaytrend.postservice.apo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAop {

   /* @Around("execution(* com.todaytrend.postservice.*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{

        try {
           return joinPoint.proceed();//다음 메소드 진행
        }catch (Exception e){
            int statusCode = chooseStatusCode(e);
            throw  new NewException("change new exception", e,statusCode);
        }
    }*/

    private int chooseStatusCode(Exception e) {
        // 상황에 따라에러 코드를 결정
        if (e instanceof RuntimeException) {
            // 특정 예외에 대한 처리 로직
            return 401;
        } else {
            return 400;
        }
    }

}
