package com.imm.business.service;

import java.util.List;

import com.imm.business.vo.AdviserVo;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.util.Page;

public interface AdviserMngService {

	Page getAdviser(int pageNum, int pageSize, String searchText, Integer proType, Long adviserId);
	boolean saveOrUpdateAdviser(AdviserVo vo,  UserMngVo userMngVo );
	public AdviserVo queryAdviserById(Long id);
	public List<AdviserVo> queryAll();
	
	Page getAdviserApplication(int pageNum, int pageSize, String searchText);

}