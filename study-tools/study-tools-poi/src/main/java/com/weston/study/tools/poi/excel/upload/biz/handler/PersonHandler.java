package com.weston.study.tools.poi.excel.upload.biz.handler;

import org.springframework.stereotype.Component;

import com.weston.study.tools.poi.excel.upload.biz.vo.PersonVO;
import com.weston.study.tools.poi.excel.upload.enums.ExcelImportBizType;
import com.weston.study.tools.poi.excel.upload.handler.AbstractExcelReaderHandler;
import com.weston.study.tools.poi.excel.upload.model.HandlerResult;
import com.weston.study.tools.poi.excel.upload.model.ViewObjectRow;

@Component
public class PersonHandler extends AbstractExcelReaderHandler<PersonVO> {

	@Override
	public ExcelImportBizType getBizType() {
		return ExcelImportBizType.Person;
	}

	@Override
	public HandlerResult handle(ViewObjectRow viewObjectRow) {
		System.out.println("读取了一条数据");
		return HandlerResult.success();
	}

}
