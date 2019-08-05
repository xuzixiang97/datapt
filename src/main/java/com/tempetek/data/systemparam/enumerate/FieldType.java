package com.tempetek.data.systemparam.enumerate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum FieldType {

	ID("id"),
	
	CODE("code");
	
	private String name;

	public String getName() {
		return name;
	}

	private FieldType(String name) {
		this.name = name;
	}

	/**
	 * 获取所有的枚举集合
	 * @return
	 */
	public static List<FieldType> getValues() {
		return new ArrayList<FieldType>(Arrays.asList(FieldType.values()));
	}

}