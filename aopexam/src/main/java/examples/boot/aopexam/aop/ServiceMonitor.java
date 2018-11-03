package examples.boot.aopexam.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component // @Aspect를 Bean으로 등록하려면 @Component가 붙어야 한다.
public class ServiceMonitor {
    // 메소드가 실행될 때 메소드 이름을 출력.
    @Before("execution(* examples..*Service.*(..))")
    public void beforeExec(JoinPoint joinPoint) {
        System.out.println("------------------------------------------------");
        System.out.println("name : " + joinPoint.getSignature().getName());
        System.out.println("------------------------------------------------");
    }

    @AfterReturning("execution(* examples..*Service.*(..))")
    public void logServiceAccess(JoinPoint joinPoint) {
        System.out.println("name : " + joinPoint.getSignature().getName());
        System.out.println("Completed: " + joinPoint);
    }

    @AfterThrowing(value = "execution(* examples..*Service.*(..))", throwing = "ex")
    public void afterException(JoinPoint joinPoint, Exception ex) {
        System.out.println("**********************************");
        System.out.println("exception :" + joinPoint);
        System.out.println(ex.getMessage());
        System.out.println("**********************************");
    }
}
