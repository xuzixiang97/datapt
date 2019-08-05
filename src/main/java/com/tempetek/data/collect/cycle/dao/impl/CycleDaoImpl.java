package com.tempetek.data.collect.cycle.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.tempetek.data.collect.cycle.dao.ICycleDao;
import com.tempetek.data.collect.cycle.entity.CycleInfo;
import com.tempetek.core.Pagination;
import com.tempetek.orm.AbstractBasicDaoImpl;

@Repository
public class CycleDaoImpl extends AbstractBasicDaoImpl<CycleInfo, Long> implements ICycleDao {
	
	@Override
	public List<CycleInfo> findByCondition(CycleInfo condition) {
		return this.selectList("findByCondition", condition);
	}

	@Override
	public Pagination<CycleInfo> findByPager(Pagination<CycleInfo> pagination, CycleInfo condition) {
		return this.findByPager(pagination, "selectPager", "countPager", condition);
	}
	
	@Override
	public void deleteByIds(Long[] ids) {
		this.delete("deleteByIds", ids);
	}
	
	@Override
	protected String getNamespace() {
		return CycleInfo.class.getName();
	}

}