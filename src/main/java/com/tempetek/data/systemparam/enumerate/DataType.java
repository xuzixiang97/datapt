package com.tempetek.data.systemparam.enumerate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum DataType {
	
	ACCOUNT("CS_ACCOUNT");

	private String name;

	public String getName() {
		return name;
	}

	private DataType(String name) {
		this.name = name;
	}

	/**
	 * 获取所有的枚举集合
	 * @return
	 */
	public static List<DataType> getValues() {
		return new ArrayList<DataType>(Arrays.asList(DataType.values()));
	}

}