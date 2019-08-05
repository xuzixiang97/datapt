package com.tempetek.data.config.validate.entity;

import com.tempetek.core.base.LongId;

import java.util.Date;

public class ValidateInfo extends LongId {

	private static final long serialVersionUID = 1L;
    // 模板ID
	private Long templateId;
	//模板名称
	private String templateStr;
    // 名称
	private String name;
    // 创建人
	private String creator;
    // 创建时间
	private Date createTime;
    // 修改人
	private String modifier;
    // 修改时间
	private Date modifiedTime;
    // 备注
	private String description;
	
	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getTemplateStr() {
		return templateStr;
	}

	public void setTemplateStr(String templateStr) {
		this.templateStr = templateStr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}