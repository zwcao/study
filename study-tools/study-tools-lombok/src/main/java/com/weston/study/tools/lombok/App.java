package com.weston.study.tools.lombok;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

	@SneakyThrows
	public static void main(String[] args) {
		Person p = new Person();
		p = new Person(null);

		log.debug("xxx");
		
		Person.builder().address("").age(1);

		@Cleanup
		InputStream in = new FileInputStream("");

		val example = new ArrayList<String>();
		example.add("Hello, World!");
		
		val a = new HashSet<>();
	}
}
