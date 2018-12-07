package com.weston.study.tools.poi.excel.upload.subcriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.weston.study.tools.poi.excel.upload.handler.AbstractExcelReaderHandler;
import com.weston.study.tools.poi.excel.upload.listener.ExcelHandlerListener;
import com.weston.study.tools.poi.excel.upload.listener.ExcelHandlerListener.ExcelHandlerEvent;
import com.weston.study.tools.poi.excel.upload.model.HandlerResult;
import com.weston.study.tools.poi.excel.upload.model.ViewObjectRow;

public class DefaultExcelReaderSubscriber implements ExcelReaderSubscriber {
	
	private List<ExcelHandlerListener> listeners = new ArrayList<>();

	private BlockingQueue<ViewObjectRow> blockingQueue;

	private AbstractExcelReaderHandler handler;
	
	private boolean isRunning = true;

	public DefaultExcelReaderSubscriber(AbstractExcelReaderHandler handler, BlockingQueue<ViewObjectRow> blockingQueue) {
		this.blockingQueue = blockingQueue;
		this.handler = handler;
	}

	@Override
	public void subscribe() {
		isRunning = true;
		while (true) {
			try {
				ViewObjectRow viewObjectRow = blockingQueue.take();
				if (viewObjectRow.isFinish()) {
					sendEvent(new ExcelHandlerEvent(ExcelHandlerEvent.EVENT_READ_COMPLETE, null, null));
					break;
				}
				HandlerResult result = handler.handle(viewObjectRow);
				sendEvent(new ExcelHandlerEvent(ExcelHandlerEvent.EVENT_READ_ROW, viewObjectRow, result));
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
		isRunning = false;
	}
	
	/**
	 * 添加监听程序
	 * 
	 * @param event
	 */
	public void addListener(ExcelHandlerListener listener) {
		listeners.add(listener);
	}

	@Override
	public void stop() {
		// 强制停止
		if (isRunning) {
			try {
				blockingQueue.put(ViewObjectRow.FINISH_COMMAND);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void sendEvent(ExcelHandlerEvent event) {
		listeners.forEach(p -> {
			p.onEvent(event);
		});
	}
}
