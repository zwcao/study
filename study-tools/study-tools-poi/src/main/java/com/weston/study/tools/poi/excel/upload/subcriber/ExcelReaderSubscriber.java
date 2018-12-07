package com.weston.study.tools.poi.excel.upload.subcriber;

import com.weston.study.tools.poi.excel.upload.listener.ExcelHandlerListener;

/**
 * 订阅者：订阅excel读取的数据
 * 
 * @author czw
 */
public interface ExcelReaderSubscriber {

	/**
	 * 订阅数据
	 * 
	 * @param rowData
	 */
	void subscribe();

	/**
	 * 停止订阅
	 */
	void stop();

	/**
	 * 添加监听程序
	 * 
	 * @param listener
	 */
	void addListener(ExcelHandlerListener listener);
}
