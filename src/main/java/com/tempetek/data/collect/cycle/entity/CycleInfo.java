package com.tempetek.data.collect.cycle.entity;

import java.util.Date;
import com.tempetek.core.base.LongId;

public class CycleInfo extends LongId {

	private static final long serialVersionUID = 1L;
    // 自检ID
	private Long validateId;
	// 自检名称
	private String validateStr;
    // 时间类型
	private String timeType;
	// 时间类型名称
	private String timeTypeStr;
    // 开始时间
	private String startTime;
    // 结束时间
	private String endTime;
    // 是否启用
	private String isEnable;
	// 是否启用名称
	private String isEnableStr;
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
	
	public Long getValidateId() {
		return validateId;
	}

	public void setValidateId(Long validateId) {
		this.validateId = validateId;
	}
	
	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
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

	public String getValidateStr() {
		return validateStr;
	}

	public void setValidateStr(String validateStr) {
		this.validateStr = validateStr;
	}

	public String getTimeTypeStr() {
		return timeTypeStr;
	}

	public void setTimeTypeStr(String timeTypeStr) {
		this.timeTypeStr = timeTypeStr;
	}

	public String getIsEnableStr() {
		return isEnableStr;
	}

	public void setIsEnableStr(String isEnableStr) {
		this.isEnableStr = isEnableStr;
	}
}