package com.tempetek.data.config.template.entity;

import com.tempetek.core.base.LongId;

import java.util.Date;

public class TemplateInfo extends LongId {

	private static final long serialVersionUID = 1L;
    // 模板编号
	private String code;
    // 模板名称
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
    // 模板类型
	private String type;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}