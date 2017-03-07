package com.imm.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imm.business.dao.CaseDao;
import com.imm.business.vo.CaseVo;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.util.Page;

@Service("caseService")
public class CaseServiceImpl implements CaseService {
	@Autowired
	public CaseDao caseDao;

	 
	public Page getCase(int pageNum, int pageSize, String searchText) {
		return caseDao.getCases(pageNum, pageSize, searchText);
	}

	 
	public boolean saveOrUpdateCase(CaseVo vo, UserMngVo userMngVo) {
		if (vo.getId() != null) {
			caseDao.update(vo);
		} else {
			caseDao.add(vo);
		}
		return true;
	}

	 
	public List<CaseVo> queryAll() {
		return caseDao.queryAll();
	}

	 
	public CaseVo queryCaseById(Long id) {
		return caseDao.queryCase(id);
	}

}
