package edu.sjsu.cmpe275.aop.aspect;
import org.aspectj.lang.annotation.Aspect;  // if needed
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;

import edu.sjsu.cmpe275.aop.exceptions.AccessDeniedExeption;

import org.aspectj.lang.annotation.Around;  // if needed
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint; // if needed

@Aspect
public class ValidationAspect implements Ordered{
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     * @throws AccessDeniedExeption 
     */

	//@Around("execution(public void edu.sjsu.cmpe275.aop.BlogService.*(..))")
	@Before("execution(public * edu.sjsu.cmpe275.aop.BlogService.*(..))")
	public void checkParam(JoinPoint joinPoint) throws IllegalArgumentException {
		Object[] arr = joinPoint.getArgs();
		for(int i = 0; i<arr.length;i++) {
			int args = arr[i].toString().length();
			//System.out.println(joinPoint.getSignature().getName());
			if((i<2) || (i == 2 && !joinPoint.getSignature().getName().equals("commentOnBlog"))) {
				if(args<3)
				throw new IllegalArgumentException("Invalid Name");
			}
			else if (joinPoint.getSignature().getName().equals("commentOnBlog") && i ==2)
					if((args> 100 || args<=0) ){
						//System.out.println("aashi");
						throw new IllegalArgumentException("Invalid Comment");
			}
		}
		
		//System.out.printf("Prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
	}

	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}
}