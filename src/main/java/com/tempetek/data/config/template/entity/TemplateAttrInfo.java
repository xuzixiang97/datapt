package com.tempetek.data.config.template.entity;

import com.tempetek.core.base.LongId;

public class TemplateAttrInfo extends LongId {

	private static final long serialVersionUID = 1L;
    // 模板ID
	private Long templateId;
    // 属性名
	private String fieldName;
    // 属性描述
	private String fieldComment;
	
	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldComment() {
		return fieldComment;
	}

	public void setFieldComment(String fieldComment) {
		this.fieldComment = fieldComment;
	}
	
}