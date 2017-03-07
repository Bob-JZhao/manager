package com.imm.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imm.business.dao.ClientDao;
import com.imm.business.vo.ClientVo;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.util.Page;

@Service("clientService")
public class ClientServiceImpl implements ClientService{
	@Autowired
	public ClientDao clientDao ;
	 
	public Page getClient(int pageNum, int pageSize, String searchText) {
		// TODO Auto-generated method stub
		return clientDao.getClient(pageNum, pageSize, searchText);
	}

	 
	public boolean saveOrUpdateClient(ClientVo vo, UserMngVo userMngVo) {
		// TODO Auto-generated method stub
		if(vo.getId() != null){
			clientDao.update(vo);
		}
		else{
			clientDao.add(vo);
		}
		if(vo.getId() != null)
			return true ;
		else
			return false;
	}

	 
	public ClientVo queryClientById(Long id) {
		return clientDao.queryClient(id);
	}

	 
	public Page getClientSearch(int pageNum, int pageSize, List<String> searchText) {
		 return clientDao.getClienSearcht(pageNum, pageSize, searchText);
	}

}
