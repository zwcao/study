package com.weston.study.rpc.sentinel.consumer;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallbackRegistry;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.SentinelRpcException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.dubbo.common.utils.NamedThreadFactory;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcResult;
import com.weston.study.rpc.sentinel.FooService;

@Component
public class DubboConsumer {

	private static final String RES_KEY = "com.weston.study.rpc.sentinel.FooService:sayHello()";
	private static final String INTERFACE_RES_KEY = "com.weston.study.rpc.sentinel.FooService";

	private static final ExecutorService pool = Executors.newFixedThreadPool(10,
			new NamedThreadFactory("dubbo-consumer-pool"));

	private static FooService fooService;

	public static void main(String[] args) {
		initFlowRule();

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.weston.study.rpc.sentinel.consumer");
//		context.register(DubboConsumerConfiguration.class);
		context.refresh();

		DubboConsumer client = context.getBean(DubboConsumer.class);
		for (int i = 0; i < 10; i++) {
			pool.submit(() -> {
				try {
					System.out.println(fooService.sayHello());
				} catch (SentinelRpcException ex) {
					System.out.println("Blocked");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
			// pool.submit(() -> System.out.println("Another: " + service.doAnother()));
		}
	}

	@Reference(url = "dubbo://127.0.0.1:25758", timeout = 3000)
	public void setFooService(FooService fooService) {
		DubboConsumer.fooService = fooService;
	}

	private static void initFlowRule() {
		FlowRule flowRule = new FlowRule();
		flowRule.setResource(RES_KEY);
		flowRule.setCount(5);
		flowRule.setGrade(RuleConstant.FLOW_GRADE_THREAD);
		flowRule.setLimitApp("default");
		FlowRuleManager.loadRules(Collections.singletonList(flowRule));
	}

	private static void registerFallback() {
		// Register fallback handler for consumer.
		// If you only want to handle degrading, you need to
		// check the type of BlockException.
		DubboFallbackRegistry.setConsumerFallback((a, b, ex) -> new RpcResult("Error: " + ex.getClass().getTypeName()));
	}

}
