package edu.sjsu.cmpe275.aop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
        /***
         * Following is a dummy implementation of App to demonstrate bean creation with Application context.
         * You may make changes to suit your need, but this file is NOT part of the submission.
         */

    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        BlogService blogService = (BlogService) ctx.getBean("blogService");

        try {
        	blogService.shareBlog("Alice", "Alice", "Bob");
        	//blogService.unshareBlog("Bob", "Alice");
        	
        	blogService.commentOnBlog("Bob", "ce", "eee");
        	/*//blogService.shareBlog("Aashi", "suchi", "Bob");
            blogService.readBlog("Bo", "Alice");
            //blogService.readBlog("tom", "tom");
            blogService.commentOnBlog("Bob", "Alice", "Nice work!");
            blogService.commentOnBlog("Bob", "Bob", "Nice work!");
            blogService.unshareBlog("Alice", "Bob");
            blogService.shareBlog("Bob", "Alice", "aaa");*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        ctx.close();
    }
}