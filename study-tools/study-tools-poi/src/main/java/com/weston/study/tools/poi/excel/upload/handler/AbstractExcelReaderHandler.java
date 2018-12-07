package com.weston.study.tools.poi.excel.upload.handler;

import com.weston.study.tools.poi.excel.upload.enums.ExcelImportBizType;
import com.weston.study.tools.poi.excel.upload.listener.ExcelHandlerListener;
import com.weston.study.tools.poi.excel.upload.model.HandlerResult;
import com.weston.study.tools.poi.excel.upload.model.ViewObjectRow;

public abstract class AbstractExcelReaderHandler<T> {

	/**
	 * 业务类型，针对对应的匹配类
	 * 
	 * @return
	 */
	public abstract ExcelImportBizType getBizType();
	
	public abstract HandlerResult handle(ViewObjectRow viewObjectRow);
	
	public ExcelHandlerListener getListener() {
		return null;
	}
}
