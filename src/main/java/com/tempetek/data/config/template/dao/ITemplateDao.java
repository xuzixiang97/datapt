package com.tempetek.data.config.template.dao;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.template.entity.TemplateInfo;
import com.tempetek.orm.AbstractBasicDao;

import java.util.List;

public interface ITemplateDao extends AbstractBasicDao<TemplateInfo, Long> {

    public TemplateInfo findByCode(String code);
	
    public List<TemplateInfo> findByCondition(TemplateInfo condition);
    
    public Pagination<TemplateInfo> findByPager(Pagination<TemplateInfo> pagination, TemplateInfo condition);
    
    public void deleteByIds(Long[] ids);
	
}