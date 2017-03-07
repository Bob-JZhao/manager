package com.imm.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imm.business.dao.MessageDao;
import com.imm.business.dao.UserMngDao;
import com.imm.business.vo.MessageVo;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.util.Page;
@Service("messageService")
public class MessageServiceImpl implements MessageService {
	@Autowired
	public MessageDao messDao;
@Autowired
public UserMngDao userDao ;
	 
	public Page getMessage(int pageNum, int pageSize, String searchText,long userId) {
		return messDao.getMessage(pageNum, pageSize, searchText,userId);
	}

	 
	public boolean saveOrUpdateMessage(MessageVo vo, UserMngVo userMngVo) {
		if(vo.getId() != null){
			messDao.update(vo);
		}
		else {
			messDao.add(vo);
		}
		return false;
	}

	 
	public MessageVo queryMessageById(Long id) {
		// TODO Auto-generated method stub
		return messDao.queryMess(id);
	}

	 
	public List<MessageVo> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	 
	public List<UserMngVo> getAllUser() {
		// TODO Auto-generated method stub
		return userDao.getAllUser();
	}

}
