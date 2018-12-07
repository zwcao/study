package com.weston.study.rpc.sentinel.provider;

import java.util.Collections;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.alibaba.csp.sentinel.init.InitExecutor;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

public class DubboProvider {

	private static final String RES_KEY = "com.weston.study.rpc.sentinel.FooService:sayHello()";
	private static final String INTERFACE_RES_KEY = "com.weston.study.rpc.sentinel.FooService";
	
	public static void main(String[] args) {
		// Users don't need to manually call this method.
		InitExecutor.doInit();

		initFlowRule();

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(DubboProviderConfiguration.class);
		context.refresh();

		System.out.println("Service provider is ready");
	}

	private static void initFlowRule() {
		FlowRule flowRule = new FlowRule();
		flowRule.setResource(RES_KEY);
		flowRule.setCount(10);
		flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		flowRule.setLimitApp("default");
		FlowRuleManager.loadRules(Collections.singletonList(flowRule));
	}

}
