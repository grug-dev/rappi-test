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
		
		Object[] args = joinPoint.getArgs();
		if (args != null && args.length > 0) {
			StringBuilder builder = new StringBuilder();
			for (Object obj : args) {
				// String.valueOf avoids a NullPointerException on null arguments.
				builder.append(String.valueOf(obj));
			}
			strArgs = builder.toString();
		}
		String method = joinPoint.getSignature().getName();
		String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
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
