package com.demo.dubbo.provider.impl;

import com.demo.dubbo.provider.DemoService;

public class DemoServiceImpl implements DemoService{

	public String sayHello(String name) {
		return "Hello "+name;
	}

}
