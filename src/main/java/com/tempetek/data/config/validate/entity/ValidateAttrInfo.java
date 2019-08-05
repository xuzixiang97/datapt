package com.tempetek.data.config.validate.entity;

import com.tempetek.core.base.LongId;

public class ValidateAttrInfo extends LongId {

	private static final long serialVersionUID = 1L;
    // 自检ID
	private Long validateId;
    // 自检名
	private String validateName;
    // 自检类型
	private String validateType;
	//自检类型名称
	private String validateTypeStr;
	// 自检长度
	private String validateLength;
    // 是否为空
	private String validateNullable;
	//是否为空名称
	private String validateNullableStr;
    // 默认值
	private String validateValue;
    // 属性描述
	private String validateComment;

	public String getValidateNullableStr() {
		return validateNullableStr;
	}

	public void setValidateNullableStr(String validateNullableStr) {
		this.validateNullableStr = validateNullableStr;
	}

	public String getValidateTypeStr() {
		return validateTypeStr;
	}

	public void setValidateTypeStr(String validateTypeStr) {
		this.validateTypeStr = validateTypeStr;
	}

	public Long getValidateId() {
		return validateId;
	}

	public void setValidateId(Long validateId) {
		this.validateId = validateId;
	}

	public String getValidateName() {
		return validateName;
	}

	public void setValidateName(String validateName) {
		this.validateName = validateName;
	}

	public String getValidateType() {
		return validateType;
	}

	public void setValidateType(String validateType) {
		this.validateType = validateType;
	}

	public String getValidateNullable() {
		return validateNullable;
	}

	public void setValidateNullable(String validateNullable) {
		this.validateNullable = validateNullable;
	}

	public String getValidateValue() {
		return validateValue;
	}

	public void setValidateValue(String validateValue) {
		this.validateValue = validateValue;
	}

	public String getValidateComment() {
		return validateComment;
	}

	public void setValidateComment(String validateComment) {
		this.validateComment = validateComment;
	}

	public String getValidateLength() {
		return validateLength;
	}

	public void setValidateLength(String validateLength) {
		this.validateLength = validateLength;
	}
}