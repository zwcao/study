package com.weston.study.tools.poi.excel.upload;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.weston.study.tools.poi.excel.upload.biz.ExcelImportor;
import com.weston.study.tools.poi.excel.upload.enums.ExcelImportBizType;

public class Application {
	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext("com.weston.study.tools.poi.excel.upload");
		// 导入
		ExcelImportor.process(ExcelImportBizType.Person, "/Users/weston/1.workspace/trials/tools/src/main/java/com/weston/tools/excel/upload/demo.xlsx");
	}
}
