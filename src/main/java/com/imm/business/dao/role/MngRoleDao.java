package com.imm.business.dao.role;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import com.imm.business.vo.role.MngRoleVo;
import com.imm.common.jdbc.AbstractJdbcDao;
import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;


@Component("mngRoleDao")
public class MngRoleDao extends AbstractJdbcDao{
	
	final static Log log = LogFactory.getLogger(MngRoleDao.class);

	public MngRoleVo getMngRoleById(Long id){
		
		String sql = "select mr.ID,mr.NAME,mr.DESC from mng_role as mr where mr.ID = ?";
		
		Object[] params = {id};
		
		List<MngRoleVo> lists = super.findList(sql, Arrays.asList(params), MngRoleVo.class);
		
		if(null!=lists && lists.size()>0){
			
			return lists.get(0);
			
		}
		
		return null;
	}

}
