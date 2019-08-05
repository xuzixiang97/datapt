package com.tempetek.data.config.validate.service.impl;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.validate.dao.IValidateAttrDao;
import com.tempetek.data.config.validate.entity.ValidateAttrInfo;
import com.tempetek.data.config.validate.service.IValidateAttrService;
import com.tempetek.orm.AbstractBasicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ValidateAttrServiceImpl extends AbstractBasicServiceImpl<ValidateAttrInfo, Long> implements IValidateAttrService {
	
    @Autowired
    private IValidateAttrDao iValidateAttrDao;
    
    @Override
	public ValidateAttrInfo findById(Long id) {
		return iValidateAttrDao.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		iValidateAttrDao.deleteById(id);
	}

	@Override
	public void insert(ValidateAttrInfo condition) {
		iValidateAttrDao.insert(condition);
	}

	@Override
	public void update(ValidateAttrInfo condition) {
		iValidateAttrDao.update(condition);
	}
	
	@Override
	public List<ValidateAttrInfo> findAll() {
		return iValidateAttrDao.findByCondition(new ValidateAttrInfo());
	}
	
	@Override
	public List<ValidateAttrInfo> findByCondition(ValidateAttrInfo condition) {
		return iValidateAttrDao.findByCondition(condition);
	}

	@Override
	public Pagination<ValidateAttrInfo> findByPager(Pagination<ValidateAttrInfo> pagination, ValidateAttrInfo condition) {
		return iValidateAttrDao.findByPager(pagination, condition);
	}
	
	@Override
	public void deleteByIds(Long[] ids) {
    	iValidateAttrDao.deleteByIds(ids);
	}

	@Override
	@Transactional
	public void save(Long validateId, List<ValidateAttrInfo> templateAttrInfos) {
		iValidateAttrDao.deleteByValidateId(validateId);

		for(ValidateAttrInfo validateAttrInfo : templateAttrInfos){
			validateAttrInfo.setValidateId(validateId);
			iValidateAttrDao.insert(validateAttrInfo);
		}
	}

}