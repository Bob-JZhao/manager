package com.imm.business.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.imm.business.vo.CaseVo;
import com.imm.business.vo.ClientVo;
import com.imm.common.jdbc.AbstractJdbcDao;
import com.imm.common.jdbc.CommonDao;
import com.imm.common.util.Page;

@Component("clientDao")
public class ClientDao extends AbstractJdbcDao {
	@Autowired
	private CommonDao commonDao;

	public Page getClient(int pageNum, int pageSize, String searchText) {
		String sql = "select id, client_name as clientName,personal_info as personalInfo ,BACKGROUND_INFO as backgroundInfo"
				+ " ,create_time as createTime  from client  where 1=1  ";
		final List<Object> paramList = new ArrayList<Object>();
		if (!StringUtils.isEmpty(searchText)) {// 用户选择了查询条件
			sql += " and (ID like ? or client_Name like ?)";
			searchText = "%" + searchText + "%";
			paramList.add(searchText);
			paramList.add(searchText);
		}

		sql += " order by id desc";
		Page page = commonDao.getPage(sql, pageNum, pageSize, paramList, ClientVo.class);
		return page;
	}

	public ClientVo queryClient(Long caseId) {
		String sql = "select  id, client_name as clientName,personal_info as personalInfo ,BACKGROUND_INFO as backgroundInfo"
				+ " from client where id = ?  ";
		final List<Object> paramList = new ArrayList<Object>();
		return (ClientVo) super.findObject(sql, caseId, ClientVo.class);

	}

	public List<ClientVo> queryAll() {
		String sql = "select * from client " + "  order by id desc  ";
		final List<Object> paramList = new ArrayList<Object>();
		return super.findList(sql, paramList, ClientVo.class);
	}

	public void add(ClientVo vo) {
		String sql = "insert into client (CLIENT_NAME,personal_info,BACKGROUND_INFO) " + "values (?,?,?)";
		List<Object> paramsList = new ArrayList<Object>();
		Object[] params = { vo.getClientName(), vo.getPersonalInfo() ,vo.getBackgroundInfo()};
		paramsList.addAll(Arrays.asList(params));
		long id = super.saveObject(sql, paramsList);
		vo.setId(id);

	}

	public boolean update(ClientVo vo) {
		boolean flag = false;
		String sql = "update sg_case set CLIENT_NAME=?,VISA_TYPE=?,VISA_OFFICE_BRANCH = ?,EXPECTED_SUB =? where id = ?";
		List<Object> paramsList = new ArrayList<Object>();
		Object[] params = { vo.getClientName(), 
				vo.getId() };
		paramsList.addAll(Arrays.asList(params));
		int row = super.doUpdate(sql, paramsList);
		if (row > 0) {
			flag = true;
		}
		return flag;
	}

	private List<Object> getParams(CaseVo vo) {
		List<Object> paramsList = new ArrayList<Object>();
		Object[] params = {};
		paramsList.addAll(Arrays.asList(params));
		if (vo.getId() != null) {
			paramsList.add(vo.getId());
		}
		return paramsList;
	}
	public Page getClienSearcht(int pageNum, int pageSize, List<String> searchText) {
		String sql = "select id, client_name as clientName,personal_info as personalInfo ,BACKGROUND_INFO as backgroundInfo,"
				+ " create_time as createTime"
				+ "  from client"
				+ "  where  PERSONAL_INFO in ("
				+ buildSqlIdString(searchText, false) + ") or BACKGROUND_INFO in ("
				+ buildSqlIdString(searchText, false) + ")  ";
		final List<Object> paramList = new ArrayList<Object>();
	 

		sql += " order by id desc";
		Page page = commonDao.getPage(sql, pageNum, pageSize, paramList, ClientVo.class);
		return page;
	}
	// 生成SQL语句需要的以逗号隔开的ID字符串
		protected String buildSqlIdString(Collection<? extends Object> idSet, boolean isLong) {
			final StringBuffer sb = new StringBuffer();
			if (isLong) {
				for (Object tmpId : idSet) {
					sb.append(tmpId + ", ");
				}
			} else {
				for (Object tmpId : idSet) {
					sb.append("'" + StringUtils.trimAllWhitespace(tmpId+"") + "', ");
				}
			}
			if (sb.length() > 0) {
				final int lastIndex = sb.lastIndexOf(",");
				sb.delete(lastIndex, sb.length());
			}
			if(sb.toString().length() == 0){
				sb.append("''");
			}
			return sb.toString();
		}
}
