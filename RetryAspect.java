package edu.sjsu.cmpe275.aop.aspect;

import org.aspectj.lang.annotation.Aspect; // if needed
import org.springframework.core.Ordered;

import edu.sjsu.cmpe275.aop.exceptions.NetworkException;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around; // if needed
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint; // if needed

@Aspect
public class RetryAspect implements Ordered {
	/***
	 * Following is a dummy implementation of this aspect. You are expected to
	 * provide an actual implementation based on the requirements, including
	 * adding/removing advices as needed.
	 */
	int count = 0;

	@Around("execution(public * edu.sjsu.cmpe275.aop.BlogService.*(..))")
	public Object networkcount(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			return joinPoint.proceed();
		} catch (NetworkException e) {
			System.out.println("retrying");
			count++;

			try {
				return joinPoint.proceed();
			} catch (NetworkException e1) {
				System.out.println("retrying");
				count++;

				try {
					return joinPoint.proceed();
				} catch (NetworkException e2) {
					throw new NetworkException("Network Exception");

				}
			}
		}
	}
	@AfterReturning("execution(public * edu.sjsu.cmpe275.aop.BlogService.*(..))")
	public void reset() {
		count = 0;
	}
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}