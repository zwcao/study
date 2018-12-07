package com.weston.study.tools.poi.excel.upload.biz.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.weston.study.tools.poi.excel.upload.annotation.ExcelCell;
import com.weston.study.tools.poi.excel.upload.annotation.ExcelWorksheet;

@ExcelWorksheet(sheetName = "人")
public class PersonVO {
	// 编号
	@ExcelCell(colIndex = 1, colName = "编号")
	private Long id;
	// 名称
	@ExcelCell(colIndex = 2, colName = "名称")
	private String name;
	// 年龄
	@ExcelCell(colIndex = 3, colName = "年龄")
	private int age;
	// 生日
	@ExcelCell(colIndex = 4, colName = "生日")
	private Date birthday;
	// 薪水
	@ExcelCell(colIndex = 5, colName = "薪水")
	private BigDecimal salary;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "PersonVO [id=" + id + ", name=" + name + ", age=" + age + ", birthday=" + birthday + ", salary="
				+ salary + "]";
	}
}
