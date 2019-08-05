package com.tempetek.data.config.validate.service.impl;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.validate.dao.IValidateAttrDao;
import com.tempetek.data.config.validate.dao.IValidateDao;
import com.tempetek.data.config.validate.entity.ValidateInfo;
import com.tempetek.data.config.validate.service.IValidateService;
import com.tempetek.orm.AbstractBasicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ValidateServiceImpl extends AbstractBasicServiceImpl<ValidateInfo, Long> implements IValidateService {
	
    @Autowired
    private IValidateDao iValidateDao;

	@Autowired
	private IValidateAttrDao iValidateAttrDao;
    
    @Override
	public ValidateInfo findById(Long id) {
		return iValidateDao.findById(id);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		iValidateAttrDao.deleteByValidateId(id);
    	iValidateDao.deleteById(id);
	}

	@Override
	public void insert(ValidateInfo condition) {
		iValidateDao.insert(condition);
	}

	@Override
	public void update(ValidateInfo condition) {
		iValidateDao.update(condition);
	}
	
	@Override
	public List<ValidateInfo> findAll() {
		return iValidateDao.findByCondition(new ValidateInfo());
	}
	
	@Override
	public List<ValidateInfo> findByCondition(ValidateInfo condition) {
		return iValidateDao.findByCondition(condition);
	}

	@Override
	public List<ValidateInfo> findNotInIds(Long[] ids) {
		return iValidateDao.findNotInIds(ids);
	}

	@Override
	public Pagination<ValidateInfo> findByPager(Pagination<ValidateInfo> pagination, ValidateInfo condition) {
		return iValidateDao.findByPager(pagination, condition);
	}
	
	@Override
	public void deleteByIds(Long[] ids) {
    	iValidateDao.deleteByIds(ids);
	}

}