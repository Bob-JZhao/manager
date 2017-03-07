package com.imm.business.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imm.business.vo.AdviserCaseVo;
import com.imm.business.vo.AdviserVo;
import com.imm.common.jdbc.AbstractJdbcDao;
import com.imm.common.jdbc.CommonDao;
import com.imm.common.util.Page;
@Component("adviserMngDao")
public class AdviserMngDao extends AbstractJdbcDao {
	@Autowired
	private CommonDao commonDao;
	public Page getAdvisers(int pageNum, int pageSize, String searchText, Integer proType, Long adviserId) {
		String sql = "select id ,name ,sex,email, mobile ,status ,birthday,create_time as createTime,user_type as userType "
				+ " from adviser  where 2 = 2  ";
		final List<Object> paramList = new ArrayList<Object>();
		if (StringUtils.isNotEmpty(searchText)) {// 用户选择了查询条件
			sql += " and (ID like ? or NAME like ?)";
			searchText = "%" + searchText + "%";
			paramList.add(searchText);
			paramList.add(searchText);
		}
		if (proType != null && proType.intValue()>=0) {
			sql += " and prd.PRD_TYPE = ?";
			paramList.add(proType);
		} 
		sql += " order by id desc";
		Page page = commonDao.getPage(sql, pageNum, pageSize, paramList, AdviserVo.class);
		return page;
	}
	public AdviserVo queryAdviser( Long adviserId) {
		String sql = "select id ,name ,sex,email, mobile ,status ,birthday,create_time as createTime"
				+ " from adviser  where id = ?  ";
		final List<Object> paramList = new ArrayList<Object>();
		return (AdviserVo) super.findObject(sql, adviserId, AdviserVo.class);

	}
	public List<AdviserVo> queryAll(){
		String sql = "select id ,name ,sex,email, mobile ,status ,birthday,create_time as createTime"
				+ " from adviser  order by name  ";
		final List<Object> paramList = new ArrayList<Object>();
		return super.findList(sql, paramList, AdviserVo.class);
	}
	public void add(AdviserVo vo) {
		String sql = "insert into adviser(login_id,NAME,sex,email,mobile,birthday) "
				+ "values (?,?,?,?,?,?)";
		List<Object> paramsList = getParams(vo);
		long id = super.saveObject(sql, paramsList);
		vo.setId(id);

	}
	public boolean update(AdviserVo vo) {
		boolean flag = false;
		String sql = "update adviser set NAME=?,email=?,mobile = ?,sex =?,birthday = ?  where id = ?"
				 ;
		List<Object> paramsList = new ArrayList<Object>();
		Object[] params = { vo.getName(),vo.getEmail(),vo.getMobile(),vo.getSex(),vo.getBirthday(),vo.getId()  };
		paramsList.addAll(Arrays.asList(params));
		int row = super.doUpdate(sql, paramsList);
		if (row > 0) {
			flag = true;
		}
		return flag;
	}
	private List<Object> getParams(AdviserVo vo) {
		List<Object> paramsList = new ArrayList<Object>();
		Object[] params = { 1,vo.getName(),vo.getSex(),vo.getEmail(),vo.getMobile(),vo.getBirthday()  };
		paramsList.addAll(Arrays.asList(params));
		if (vo.getId() != null) {
			paramsList.add(vo.getId());
		}
		return paramsList;
	}

	public Page getAdviserApplication(int pageNum, int pageSize, String searchText) {
		String sql = "select ma.id ,ma.adviser_id as adviserId ,ad.name as adviserName ,"
				+ " sc.id as caseId , sc.client_name as clientName ,sc.create_time as createTime"
				+ ", ma.status as status"
				+ " from mng_adviser_case ma ,adviser ad ,sg_case sc   where ma.adviser_id = ad.id and "
				+ " ma.case_id = sc.id  ";
		final List<Object> paramList = new ArrayList<Object>();
		if (StringUtils.isNotEmpty(searchText)) {// 用户选择了查询条件
			sql += " and (ma.ID like ? or ad.NAME like ?)";
			searchText = "%" + searchText + "%";
			paramList.add(searchText);
			paramList.add(searchText);
		}
		 
		sql += " order by ma.id desc";
		Page page = commonDao.getPage(sql, pageNum, pageSize, paramList, AdviserCaseVo.class);
		return page;
	}
}
