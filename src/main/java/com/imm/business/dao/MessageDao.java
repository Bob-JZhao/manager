package com.imm.business.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imm.business.vo.MessageVo;
import com.imm.common.jdbc.AbstractJdbcDao;
import com.imm.common.jdbc.CommonDao;
import com.imm.common.util.Page;

@Component("messageDao")
public class MessageDao extends AbstractJdbcDao {
	@Autowired
	private CommonDao commonDao;

	public Page getMessage(int pageNum, int pageSize, String searchText,long userId) {
		String sql = "select  m.id , m.from_id as fromId ,mng.name as fromName ,m.to_id as toId ,m.to_name as toName,m.create_time as"
				+ " createTime ,m.status,m.title,m.content from message  m left "
				+ "join mng_user mng on m.from_id = mng.id  where   m.to_id = ? ";
		final List<Object> paramList = new ArrayList<Object>();
		paramList.add(userId);
		if (StringUtils.isNotEmpty(searchText)) {// 用户选择了查询条件
			sql += " and (m.title like ? or m.content like ?)";
			searchText = "%" + searchText + "%";
			paramList.add(searchText);
			paramList.add(searchText);
		}

		sql += " order by id desc";
		Page page = commonDao.getPage(sql, pageNum, pageSize, paramList, MessageVo.class);
		return page;
	}

	public MessageVo queryMess(Long caseId) {
		String sql ="select m.id , m.from_id as fromId ,mng.name as fromName ,m.to_id as toId ,m.to_name as toName,m.create_time as"
				+ " createTime ,m.status,m.title,m.content from message  m left "
				+ "join mng_user mng on m.from_id = mng.id  where m.id = ? ";
		return (MessageVo) super.findObject(sql, caseId, MessageVo.class);

	}

	public List<MessageVo> queryAll() {
		String sql = "select  id , from_id as fromId ,from_name as fromName ,to_id as toId ,to_name as toName,create_time as"
				+ " createTime ,status from message  where 1=1 ";
		final List<Object> paramList = new ArrayList<Object>();
		return super.findList(sql, paramList, MessageVo.class);
	}

	public void add(MessageVo vo) {
		String sql = "insert into message (from_id,from_name,to_id,to_name,status,title,content) " 
	+ "values (?,?,?,?,?,?,?)";
		List<Object> paramsList = new ArrayList<Object>();
		Object[] params = { vo.getFromId(), vo.getFromName(), vo.getToId(), vo.getToName(), 0,
				vo.getTitle(), vo.getContent() };
		paramsList.addAll(Arrays.asList(params));
		long id = super.saveObject(sql, paramsList);
		vo.setId(id);

	}

	public boolean update(MessageVo vo) {
		boolean flag = false;
		String sql = "update message set status = ? where id = ?";
		List<Object> paramsList = new ArrayList<Object>();
		Object[] params = { 1, vo.getId() };
		paramsList.addAll(Arrays.asList(params));
		int row = super.doUpdate(sql, paramsList);
		if (row > 0) {
			flag = true;
		}
		return flag;
	}

	private List<Object> getParams(MessageVo vo) {
		List<Object> paramsList = new ArrayList<Object>();
		Object[] params = {};
		paramsList.addAll(Arrays.asList(params));
		if (vo.getId() != null) {
			paramsList.add(vo.getId());
		}
		return paramsList;
	}

}
