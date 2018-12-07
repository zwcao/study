package com.weston.study.rpc.sentinel.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.weston.study.rpc.sentinel.FooService;

@Service
public class FooServiceImpl implements FooService {
	
	@Override
	public String sayHello() {
		return "hello";
	}
	
}
