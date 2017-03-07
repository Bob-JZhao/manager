package com.imm.business.service;

import java.util.List;

import com.imm.business.vo.CaseVo;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.util.Page;

public interface CaseService {

	Page getCase(int pageNum, int pageSize, String searchText);
	boolean saveOrUpdateCase(CaseVo vo,  UserMngVo userMngVo );
	public CaseVo queryCaseById(Long id);
	public List<CaseVo> queryAll();
}