package com.weston.study.tools.poi.excel.upload.biz;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.weston.study.tools.poi.excel.upload.enums.ExcelImportBizType;
import com.weston.study.tools.poi.excel.upload.handler.AbstractExcelReaderHandler;
import com.weston.study.tools.poi.excel.upload.handler.ExcelImportHandler;
import com.weston.study.tools.poi.excel.upload.model.ViewObjectReflectData;
import com.weston.study.tools.poi.exception.Guard;

@Component
public class ExcelImportor {

	@Autowired
	private ApplicationContext applicationContext;
	
	private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 1, 2, TimeUnit.HOURS, new ArrayBlockingQueue<>(1));

	// handler处理器
	private static final Map<ExcelImportBizType, AbstractExcelReaderHandler<?>> handlerMap = new HashMap<>();
	
	private static final Map<ExcelImportBizType, ViewObjectReflectData> reflectMap = new HashMap<>();

	@PostConstruct
	private void afterPropertiesSet() {
		Map<String, AbstractExcelReaderHandler> beanMap = applicationContext.getBeansOfType(AbstractExcelReaderHandler.class);
		beanMap.entrySet().stream().forEach(e -> {
			ExcelImportBizType bizType = e.getValue().getBizType();
			if (bizType == null) {
				return;
			}
			System.out.println("已加载" + e.getKey());
			handlerMap.put(e.getValue().getBizType(), e.getValue());
		});

	}

	public static void process(ExcelImportBizType bizType, String filename) {
		threadPool.execute(() -> {
			AbstractExcelReaderHandler handler = handlerMap.get(bizType);
			Guard.notNull(handler, "业务类型'" + bizType.getName() + "'未找到对应处理器");
			
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(filename);
				new ExcelImportHandler().handle(handler, inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		threadPool.shutdown();
	}
}
