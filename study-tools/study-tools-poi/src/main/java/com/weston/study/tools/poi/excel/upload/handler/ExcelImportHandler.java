package com.weston.study.tools.poi.excel.upload.handler;

import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.weston.study.tools.poi.excel.support.ExcelObjectReflector;
import com.weston.study.tools.poi.excel.upload.listener.ExcelHandlerListener;
import com.weston.study.tools.poi.excel.upload.listener.ProgressListener;
import com.weston.study.tools.poi.excel.upload.model.ViewObjectRow;
import com.weston.study.tools.poi.excel.upload.parser.BigExcelParser;
import com.weston.study.tools.poi.excel.upload.parser.ExcelReaderContext;
import com.weston.study.tools.poi.excel.upload.publisher.DefaultExcelReaderPublisher;
import com.weston.study.tools.poi.excel.upload.publisher.ExcelReaderPublisher;
import com.weston.study.tools.poi.excel.upload.subcriber.DefaultExcelReaderSubscriber;
import com.weston.study.tools.poi.excel.upload.subcriber.ExcelReaderSubscriber;
import com.weston.study.tools.poi.exception.Guard;
import com.weston.study.tools.poi.utils.ReflectionUtils;

public class ExcelImportHandler {

	private BlockingQueue<ViewObjectRow> blockingQueue = new LinkedBlockingQueue<>();

	private ExcelReaderContext context = new ExcelReaderContext();

	public void handle(AbstractExcelReaderHandler handler, InputStream inputStream) {
		ExcelHandlerListener progressListener = new ProgressListener(context);
		
		Class<?> voClazz = ReflectionUtils.getSuperClassGenricType(handler.getClass());
		Guard.notNull(voClazz, handler.getClass().getSimpleName() + "处理器未设置转换VO对象");
		ExcelObjectReflector reflector = ExcelObjectReflector.forClass(voClazz);
		Guard.isFalse(ExcelObjectReflector.isEmpty(reflector), voClazz.getSimpleName() + "未配置Excel读取信息");
		context.setReflector(reflector);
		
		ExcelReaderPublisher publisher = new DefaultExcelReaderPublisher(context, blockingQueue);
		publisher.addListener(progressListener);
		ExcelReaderSubscriber subscriber = new DefaultExcelReaderSubscriber(handler, blockingQueue);
		subscriber.addListener(progressListener);
		
		// 添加处理器监听器
		ExcelHandlerListener handlerListenr = handler.getListener();
		if (handler != null) {
			publisher.addListener(handlerListenr);
			subscriber.addListener(handlerListenr);
		}

		// 初始化解析器
		BigExcelParser parser = new BigExcelParser(inputStream, publisher);
		// 默认从第二行开始
		parser.setFromRowIndex(2);
		// 列
		parser.setColumnIndexs(null);
		
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 1, 2, TimeUnit.HOURS, new ArrayBlockingQueue<>(1));
		try {
			threadPool.submit(() -> {
				// 启动订阅者
				subscriber.subscribe();
			});
			// 启动读取
			if (reflector.getWorkSeetMeta().sheetName != null) {
				parser.read(reflector.getWorkSeetMeta().sheetName);
			} else {
				parser.read(reflector.getWorkSeetMeta().sheetIndex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			subscriber.stop();
			threadPool.shutdown();
		}
	}
}
