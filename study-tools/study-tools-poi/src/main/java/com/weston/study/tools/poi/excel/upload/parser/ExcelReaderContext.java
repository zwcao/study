package com.weston.study.tools.poi.excel.upload.parser;

import java.util.ArrayList;
import java.util.List;

import com.weston.study.tools.poi.excel.support.ExcelObjectReflector;
import com.weston.study.tools.poi.excel.upload.model.ErrorRowData;

public class ExcelReaderContext {
	
	private ExcelObjectReflector reflector;

	private List<ErrorRowData> errors = new ArrayList<>();

	public List<ErrorRowData> getErrors() {
		return errors;
	}

	public ExcelObjectReflector getReflector() {
		return reflector;
	}

	public void setReflector(ExcelObjectReflector reflector) {
		this.reflector = reflector;
	}
}
