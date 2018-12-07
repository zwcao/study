package com.weston.study.boot.druid.starter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SlaverDBAspect implements Ordered {

	private static final Log logger = LogFactory.getLog(SlaverDBAspect.class);

	@Around("@annotation(com.weston.study.boot.druid.starter.SlaverDB)")
	public Object proceed(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		try {
			logger.info("set database connection to read only");
			DBContextHolder.setDbType(DBContextHolder.DbType.SLAVE);
			Object result = proceedingJoinPoint.proceed();
			return result;
		} finally {
			DBContextHolder.reset();
			logger.info("restore database connection");
		}
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
