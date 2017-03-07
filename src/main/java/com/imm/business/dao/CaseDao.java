package com.imm.business.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imm.business.vo.CaseVo;
import com.imm.common.jdbc.AbstractJdbcDao;
import com.imm.common.jdbc.CommonDao;
import com.imm.common.util.Page;
@Component("caseDao")
public class CaseDao extends AbstractJdbcDao {
	@Autowired
	private CommonDao commonDao;
	public Page getCases(int pageNum, int pageSize, String searchText) {
		String sql = "select id, client_name as clientName,visa_type as visaType,visa_office_branch "
				+ "as visaOfficebranch, sign_up_time as signUpTime , critical_date as criticalDate, "
				+ " create_time as createTime ,status ,EXPECTED_SUB as expectedSub from sg_case  where 1=1  ";
		final List<Object> paramList = new ArrayList<Object>();
		if (StringUtils.isNotEmpty(searchText)) {// 用户选择了查询条件
			sql += " and (ID like ? or NAME like ?)";
			searchText = "%" + searchText + "%";
			paramList.add(searchText);
			paramList.add(searchText);
		}
		  
		sql += " order by id desc";
		Page page = commonDao.getPage(sql, pageNum, pageSize, paramList, CaseVo.class);
		return page;
	}
	public CaseVo queryCase( Long caseId) {
		String sql = "select id ,name ,sex,email, mobile ,status ,birthday,create_time as createTime"
				+ " from sg_case  where id = ?  ";
		final List<Object> paramList = new ArrayList<Object>();
		return (CaseVo) super.findObject(sql, caseId, CaseVo.class);

	}
	public List<CaseVo> queryAll(){
		String sql = "select * from sg_case "
				+ "  order by id desc  ";
		final List<Object> paramList = new ArrayList<Object>();
		return super.findList(sql, paramList, CaseVo.class);
	}
	public void add(CaseVo vo) {
		String sql = "insert into sg_case (CLIENT_NAME,VISA_TYPE,VISA_OFFICE_BRANCH,"
				+ "EXPECTED_SUB) "
				+ "values (?,?,?,?)";
		List<Object> paramsList = new ArrayList<Object>();
		Object[] params = { vo.getClientName(),vo.getVisaType(),vo.getVisaOfficebranch(),vo.getExpectedSub()  };
		paramsList.addAll(Arrays.asList(params));
		long id = super.saveObject(sql, paramsList);
		vo.setId(id);

	}
	public boolean update(CaseVo vo) {
		boolean flag = false;
		String sql = "update sg_case set CLIENT_NAME=?,VISA_TYPE=?,VISA_OFFICE_BRANCH = ?,EXPECTED_SUB =? where id = ?"
				 ;
		List<Object> paramsList = new ArrayList<Object>();
		Object[] params = { vo.getClientName(),vo.getVisaType(),vo.getVisaOfficebranch(),vo.getExpectedSub(),vo.getId()   };
		paramsList.addAll(Arrays.asList(params));
		int row = super.doUpdate(sql, paramsList);
		if (row > 0) {
			flag = true;
		}
		return flag;
	}
	private List<Object> getParams(CaseVo vo) {
		List<Object> paramsList = new ArrayList<Object>();
		Object[] params = {  };
		paramsList.addAll(Arrays.asList(params));
		if (vo.getId() != null) {
			paramsList.add(vo.getId());
		}
		return paramsList;
	}

}
