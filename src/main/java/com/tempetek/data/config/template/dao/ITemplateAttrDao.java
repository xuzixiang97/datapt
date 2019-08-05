package com.tempetek.data.config.template.dao;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.template.entity.TemplateAttrInfo;
import com.tempetek.orm.AbstractBasicDao;

import java.util.List;

public interface ITemplateAttrDao extends AbstractBasicDao<TemplateAttrInfo, Long> {
	
    public List<TemplateAttrInfo> findByCondition(TemplateAttrInfo condition);
    
    public Pagination<TemplateAttrInfo> findByPager(Pagination<TemplateAttrInfo> pagination, TemplateAttrInfo condition);
    
    public void deleteByIds(Long[] ids);

    public void deleteByTemplateId(long templateId);
	
}