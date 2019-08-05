package com.tempetek.data.config.validate.dao.impl;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.validate.dao.IValidateAttrDao;
import com.tempetek.data.config.validate.entity.ValidateAttrInfo;
import com.tempetek.orm.AbstractBasicDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ValidateAttrDaoImpl extends AbstractBasicDaoImpl<ValidateAttrInfo, Long> implements IValidateAttrDao {
	
	@Override
	public List<ValidateAttrInfo> findByCondition(ValidateAttrInfo condition) {
		return this.selectList("findByCondition", condition);
	}

	@Override
	public Pagination<ValidateAttrInfo> findByPager(Pagination<ValidateAttrInfo> pagination, ValidateAttrInfo condition) {
		return this.findByPager(pagination, "selectPager", "countPager", condition);
	}
	
	@Override
	public void deleteByIds(Long[] ids) {
		this.delete("deleteByIds", ids);
	}

	@Override
	public void deleteByValidateId(long validateId) {
		this.delete("deleteByValidateId", validateId);
	}

	@Override
	protected String getNamespace() {
		return ValidateAttrInfo.class.getName();
	}

}