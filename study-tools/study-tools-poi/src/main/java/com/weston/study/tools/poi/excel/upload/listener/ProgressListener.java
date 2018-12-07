package com.weston.study.tools.poi.excel.upload.listener;

import com.weston.study.tools.poi.excel.upload.parser.ExcelReaderContext;

/**
 * 跟踪进度, 并且保存结果
 * 
 * @author czw
 */
public class ProgressListener implements ExcelHandlerListener {

	private ExcelReaderContext context;

	public ProgressListener(ExcelReaderContext context) {
		super();
		this.context = context;
	}

	@Override
	public void onEvent(ExcelHandlerEvent event) {
		if ((ExcelHandlerEvent.EVENT_READ_ROW & event.getEventType()) != 0) {
			System.out.println("成功读取一行" + event.getViewObjectRow());
		} else if(((ExcelHandlerEvent.EVENT_READ_ROW_ERROR & event.getEventType()) != 0)) {
			System.out.println("读取一行错误" + event.getResult().getMessage());
		} else if(((ExcelHandlerEvent.EVENT_READ_COMPLETE & event.getEventType()) != 0)) {
			System.out.println("完毕");
			System.out.println(context.getErrors());
		}
	}
}
