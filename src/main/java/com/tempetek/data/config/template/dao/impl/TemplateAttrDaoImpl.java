package com.tempetek.data.config.template.dao.impl;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.template.dao.ITemplateAttrDao;
import com.tempetek.data.config.template.entity.TemplateAttrInfo;
import com.tempetek.orm.AbstractBasicDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TemplateAttrDaoImpl extends AbstractBasicDaoImpl<TemplateAttrInfo, Long> implements ITemplateAttrDao {
	
	@Override
	public List<TemplateAttrInfo> findByCondition(TemplateAttrInfo condition) {
		return this.selectList("findByCondition", condition);
	}

	@Override
	public Pagination<TemplateAttrInfo> findByPager(Pagination<TemplateAttrInfo> pagination, TemplateAttrInfo condition) {
		return this.findByPager(pagination, "selectPager", "countPager", condition);
	}
	
	@Override
	public void deleteByIds(Long[] ids) {
		this.delete("deleteByIds", ids);
	}
	
	@Override
	protected String getNamespace() {
		return TemplateAttrInfo.class.getName();
	}

	@Override
	public void deleteByTemplateId(long templateId){
		this.delete("deleteByTemplateId", templateId);
	}

}