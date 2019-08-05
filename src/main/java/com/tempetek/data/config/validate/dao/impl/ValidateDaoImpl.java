package com.tempetek.data.config.validate.dao.impl;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.validate.dao.IValidateDao;
import com.tempetek.data.config.validate.entity.ValidateInfo;
import com.tempetek.orm.AbstractBasicDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ValidateDaoImpl extends AbstractBasicDaoImpl<ValidateInfo, Long> implements IValidateDao {
	
	@Override
	public List<ValidateInfo> findByCondition(ValidateInfo condition) {
		return this.selectList("findByCondition", condition);
	}

	@Override
	public List<ValidateInfo> findNotInIds(Long[] ids) {
		return this.selectList("findNotInIds",ids);
	}

	@Override
	public Pagination<ValidateInfo> findByPager(Pagination<ValidateInfo> pagination, ValidateInfo condition) {
		return this.findByPager(pagination, "selectPager", "countPager", condition);
	}
	
	@Override
	public void deleteByIds(Long[] ids) {
		this.delete("deleteByIds", ids);
	}
	
	@Override
	protected String getNamespace() {
		return ValidateInfo.class.getName();
	}

}