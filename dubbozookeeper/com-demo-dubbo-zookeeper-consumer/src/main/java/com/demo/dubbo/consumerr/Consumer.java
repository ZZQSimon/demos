package com.demo.dubbo.consumerr;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.demo.dubbo.provider.DemoService;

public class Consumer {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "configs/spring-mvc.xml" });
		context.start();

		DemoService demoService = (DemoService) context.getBean("demoService");

		String hello = demoService.sayHello("hejingyuan");
		System.out.println(hello);

		System.in.read();
	}
}
