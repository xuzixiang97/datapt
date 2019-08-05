package com.tempetek.data.collect.cycle.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tempetek.data.collect.cycle.dao.ICycleDao;
import com.tempetek.data.collect.cycle.entity.CycleInfo;
import com.tempetek.data.collect.cycle.service.ICycleService;
import com.tempetek.core.Pagination;
import com.tempetek.orm.AbstractBasicServiceImpl;

@Service
public class CycleServiceImpl extends AbstractBasicServiceImpl<CycleInfo, Long> implements ICycleService {
	
    @Autowired
    private ICycleDao iCycleDao;
    
    @Override
	public CycleInfo findById(Long id) {
		return iCycleDao.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		iCycleDao.deleteById(id);
	}

	@Override
	public void insert(CycleInfo condition) {
		iCycleDao.insert(condition);
	}

	@Override
	public void update(CycleInfo condition) {
		iCycleDao.update(condition);
	}
	
	@Override
	public List<CycleInfo> findAll() {
		return iCycleDao.findByCondition(new CycleInfo());
	}
	
	@Override
	public List<CycleInfo> findByCondition(CycleInfo condition) {
		return iCycleDao.findByCondition(condition);
	}

	@Override
	public Pagination<CycleInfo> findByPager(Pagination<CycleInfo> pagination, CycleInfo condition) {
		return iCycleDao.findByPager(pagination, condition);
	}
	
	@Override
	public void deleteByIds(Long[] ids) {
    	iCycleDao.deleteByIds(ids);
	}

}