package com.weston.study.tools.lombok;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude={"address"})
@Builder
public class Person implements Serializable {

	@NonNull
	private Long id;
	@NonNull
	private String name;
	@NonNull
	private Integer age;
	@NonNull
	private BigDecimal salary;
	@NonNull
	private Date birthday;
	@Setter(AccessLevel.PACKAGE)
	private String address;
	
	public Person(@NonNull String name) {
		this.name = name;
	}

	@ToString(includeFieldNames = true)
	@Data(staticConstructor = "of")
	public static class Work<T> {
		private final String name;
		private final T value;
	}

	public void m() {
		val example = new ArrayList<String>();
		example.add("Hello, World!");
	}
}