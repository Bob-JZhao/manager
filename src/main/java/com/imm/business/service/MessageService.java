package com.imm.business.service;

import java.util.List;

import com.imm.business.vo.MessageVo;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.util.Page;

public interface MessageService {

	Page getMessage(int pageNum, int pageSize, String searchText,long userId);
	boolean saveOrUpdateMessage(MessageVo vo,  UserMngVo userMngVo );
	public MessageVo queryMessageById(Long id);
	public List<MessageVo> queryAll();
	public List<UserMngVo> getAllUser();
}