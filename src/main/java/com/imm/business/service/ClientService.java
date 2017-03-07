package com.imm.business.service;

import java.util.List;

import com.imm.business.vo.ClientVo;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.util.Page;

public interface ClientService {

	Page getClient(int pageNum, int pageSize, String searchText);
	Page getClientSearch(int pageNum, int pageSize, List<String> searchText);
	boolean saveOrUpdateClient(ClientVo vo,  UserMngVo userMngVo );
	public ClientVo queryClientById(Long id);
	 
}