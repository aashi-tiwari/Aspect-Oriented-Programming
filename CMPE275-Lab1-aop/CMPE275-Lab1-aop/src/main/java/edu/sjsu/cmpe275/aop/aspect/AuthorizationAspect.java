package edu.sjsu.cmpe275.aop.aspect;

import org.springframework.beans.factory.annotation.Autowired;  // if needed
import org.springframework.core.Ordered;

import edu.sjsu.cmpe275.aop.BlogService;
import edu.sjsu.cmpe275.aop.exceptions.AccessDeniedExeption;

import org.aspectj.lang.annotation.Aspect;  // if needed

import java.awt.List;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;

import org.aspectj.lang.JoinPoint;  // if needed
import org.aspectj.lang.annotation.After;  // if needed
import org.aspectj.lang.annotation.Before;  // if needed


@Aspect
public class AuthorizationAspect implements Ordered {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advises as needed.
     */

	HashMap<String, ArrayList<String>> hmap = new HashMap<String,ArrayList<String>>();
	
	@Before("execution(public void edu.sjsu.cmpe275.aop.BlogService.shareBlog(..))")
	public void shareblog(JoinPoint joinPoint) throws AccessDeniedExeption {
		Object[] arr = joinPoint.getArgs();
		String userId = arr[0].toString();
		String blogId = arr[1].toString();
		String targetId = arr[2].toString();
		ArrayList <String> lst = new ArrayList<String>();
		if(!hmap.containsKey(blogId)) {
			lst.add(blogId);
			hmap.put(blogId, lst);
		}
		if(hmap.get(blogId).contains(userId)) {
			hmap.get(blogId).add(targetId);
			return;
		}
			throw new AccessDeniedExeption("Exeption occured Access is Denied");
	
	}
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.BlogService.readBlog(..))")
	public void readBlog(JoinPoint joinPoint) throws AccessDeniedExeption {
		Object[] arr = joinPoint.getArgs();
		String userId = arr[0].toString();
		String blogId = arr[1].toString();
		
		if(userId!=blogId) {
			if(!hmap.containsKey(blogId) || !hmap.get(blogId).contains(userId)) {
				throw new AccessDeniedExeption("Exeption occured Access is Denied");
			}
		}
		
		//System.out.printf("Before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
	}
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.BlogService.commentOnBlog(..))")
	public void commentBlog(JoinPoint joinPoint) throws AccessDeniedExeption {
		Object[] arr = joinPoint.getArgs();
		String userId = arr[0].toString();
		String targetId = arr[1].toString();
		if(userId!=targetId) {
			if(!hmap.containsKey(targetId) || !hmap.get(targetId).contains(userId)) {
				throw new AccessDeniedExeption("Exeption occured Access is Denied");
			}
		}
		//System.out.printf("Before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
	}
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.BlogService.unshareBlog(..))")
	public void unshareBlog(JoinPoint joinPoint) throws AccessDeniedExeption {
		Object[] arr = joinPoint.getArgs();
		String userId = arr[0].toString();
		String targetId = arr[1].toString();
		if(userId.equals(targetId)) {
			return;
			}
		if(hmap.containsKey(userId) && hmap.get(userId).contains(targetId)) {
			hmap.get(userId).remove(targetId);
			return;
		}
		throw new AccessDeniedExeption("Access is Denied");
		//System.out.printf("Before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
	}

	public int getOrder() {
		// TODO Auto-generated method stub
		return 2;
	}
	
}
