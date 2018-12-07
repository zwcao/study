package com.weston.study.tools.poi.excel.upload.publisher;

import com.weston.study.tools.poi.excel.upload.listener.ExcelHandlerListener;
import com.weston.study.tools.poi.excel.upload.listener.ExcelParserListener;
import com.weston.study.tools.poi.excel.upload.model.ExcelRowData;

/**
 * 发布者，直接与解析器对接，负责解析器所有事件的转发，以及数据的发布
 * 
 * @author czw
 */
public interface ExcelReaderPublisher extends ExcelParserListener {

	/**
	 * 发布数据
	 * 
	 * @param rowData
	 */
	void publish(ExcelRowData rowData);

	/**
	 * 添加监听者
	 * 
	 * @param listener
	 */
	void addListener(ExcelHandlerListener listener);
}
