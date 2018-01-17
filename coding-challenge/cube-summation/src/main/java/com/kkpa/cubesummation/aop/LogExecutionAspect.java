package com.kkpa.cubesummation.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogExecutionAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogExecutionAspect.class);

	@Around("@annotation(LogExecutionTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		String strArgs = "NO ARGS";
		
		Object[] args = joinPoint.getArgs() != null ? joinPoint.getArgs() : null;
		if (args != null) {
			StringBuffer buffer = new StringBuffer();
			for (Object obj : args) {
				buffer.append(obj.toString());
			}
			strArgs = buffer.toString();
		}
		String method = joinPoint.getSignature().getName();
		String className =  joinPoint.getSignature().getName() ;
		String initInfo = String.format("[INICIO] [%s] - [%s] - [%s]", className, method, strArgs);
		LOGGER.info(initInfo); 
	    
		// Execute the method intercepted
		Object proceed = joinPoint.proceed();
	 
	    long executionTime = System.currentTimeMillis() - start;
	 
	    String endInfo = String.format("[FIN] [%s] - [%s] - [%s]", className, method, " executed in " + executionTime + "ms");
	    LOGGER.info(endInfo);
	    
	    return proceed;
	}
	
}
