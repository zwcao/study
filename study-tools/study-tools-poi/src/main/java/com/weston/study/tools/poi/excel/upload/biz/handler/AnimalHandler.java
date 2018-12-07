package com.weston.study.tools.poi.excel.upload.biz.handler;

import org.springframework.stereotype.Component;

import com.weston.study.tools.poi.excel.upload.biz.vo.PersonVO;
import com.weston.study.tools.poi.excel.upload.enums.ExcelImportBizType;
import com.weston.study.tools.poi.excel.upload.handler.AbstractExcelReaderHandler;
import com.weston.study.tools.poi.excel.upload.model.HandlerResult;
import com.weston.study.tools.poi.excel.upload.model.ViewObjectRow;

@Component
public class AnimalHandler extends AbstractExcelReaderHandler<PersonVO> {

	@Override
	public ExcelImportBizType getBizType() {
		return ExcelImportBizType.Animal;
	}

	@Override
	public HandlerResult handle(ViewObjectRow viewObjectRow) {
		return HandlerResult.success();
	}

}
