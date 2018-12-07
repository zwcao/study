package com.weston.study.tools.poi.excel.upload.biz.vo;

import com.weston.study.tools.poi.excel.upload.annotation.ExcelCell;
import com.weston.study.tools.poi.excel.upload.annotation.ExcelWorksheet;

@ExcelWorksheet(sheetIndex = 1)
public class AnimalVO {
	// 名称
	@ExcelCell(colIndex = 1, colName = "名称")
	private String name;
	@ExcelCell(colIndex = 2, colName = "种类")
	private String kind;
	@ExcelCell(colIndex = 3, colName = "年龄")
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "AnimalVO [name=" + name + ", kind=" + kind + ", age=" + age + "]";
	}
}
