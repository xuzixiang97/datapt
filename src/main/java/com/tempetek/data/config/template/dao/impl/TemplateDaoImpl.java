package com.tempetek.data.config.template.dao.impl;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.template.dao.ITemplateDao;
import com.tempetek.data.config.template.entity.TemplateInfo;
import com.tempetek.orm.AbstractBasicDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TemplateDaoImpl extends AbstractBasicDaoImpl<TemplateInfo, Long> implements ITemplateDao {

	@Override
	public TemplateInfo findByCode(String code){
		return this.selectOne("findByCode",code);
	};
	
	@Override
	public List<TemplateInfo> findByCondition(TemplateInfo condition) {
		return this.selectList("findByCondition", condition);
	}

	@Override
	public Pagination<TemplateInfo> findByPager(Pagination<TemplateInfo> pagination, TemplateInfo condition) {
		return this.findByPager(pagination, "selectPager", "countPager", condition);
	}
	
	@Override
	public void deleteByIds(Long[] ids) {
		this.delete("deleteByIds", ids);
	}
	
	@Override
	protected String getNamespace() {
		return TemplateInfo.class.getName();
	}

}