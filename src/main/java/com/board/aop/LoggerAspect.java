package com.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component //스프링 컨테이너에 빈으로 등록/ 개발자가 직접 정의한 클래스를 빈(Bean)으로 등록할 때 사용/ @Bean은 개발자가 제어할 수 없는 외부 라이브러리를 빈(Bean)으로 등록할 때 사용
@Aspect 
public class LoggerAspect {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//메서드의 호출 자체를 제어할 수 있는 어드바이스
	@Around("execution(* com.board..controller.*Controller.*(..)) or execution(* com.board..service.*Impl.*(..)) or execution(* com.board..mapper.*Mapper.*(..))")
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
		
		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();
		
		if (name.contains("Controller") == true) {
			type = "Controller ===>";
		} else if (name.contains("Service") == true) {
			type = "ServiceImpl ===>";
		} else if (name.contains("Mapper") == true) {
			type = "Mapper ===>";
		}
		
		logger.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
	
}
