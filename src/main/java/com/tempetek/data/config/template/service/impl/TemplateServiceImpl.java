package com.tempetek.data.config.template.service.impl;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.template.dao.ITemplateAttrDao;
import com.tempetek.data.config.template.dao.ITemplateDao;
import com.tempetek.data.config.template.entity.TemplateInfo;
import com.tempetek.data.config.template.service.ITemplateService;
import com.tempetek.orm.AbstractBasicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TemplateServiceImpl extends AbstractBasicServiceImpl<TemplateInfo, Long> implements ITemplateService {

    @Autowired
    private ITemplateDao iTemplateDao;

	@Autowired
	private ITemplateAttrDao iTemplateAttrDao;

	@Override
	public TemplateInfo findByCode(String code){
		return iTemplateDao.findByCode(code);
	}
    
    @Override
	public TemplateInfo findById(Long id) {
		return iTemplateDao.findById(id);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		iTemplateAttrDao.deleteByTemplateId(id);
		iTemplateDao.deleteById(id);
	}

	@Override
	public void insert(TemplateInfo condition) {
		iTemplateDao.insert(condition);
	}

	@Override
	public void update(TemplateInfo condition) {
		iTemplateDao.update(condition);
	}
	
	@Override
	public List<TemplateInfo> findAll() {
		return iTemplateDao.findByCondition(new TemplateInfo());
	}
	
	@Override
	public List<TemplateInfo> findByCondition(TemplateInfo condition) {
		return iTemplateDao.findByCondition(condition);
	}

	@Override
	public Pagination<TemplateInfo> findByPager(Pagination<TemplateInfo> pagination, TemplateInfo condition) {
		return iTemplateDao.findByPager(pagination, condition);
	}
	
	@Override
	public void deleteByIds(Long[] ids) {
    	iTemplateDao.deleteByIds(ids);
	}

}