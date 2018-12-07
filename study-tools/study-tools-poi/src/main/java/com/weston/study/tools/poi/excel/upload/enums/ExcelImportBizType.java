package com.weston.study.tools.poi.excel.upload.enums;

public enum ExcelImportBizType {

	Person("人", "Person"),
	Animal("动物", "Animal");
	
	private String name;
	private String code;
	
	private ExcelImportBizType(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
}
