package com.imm.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imm.business.dao.AdviserMngDao;
import com.imm.business.vo.AdviserVo;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.util.Page;
@Service("adviserMngService")
public class AdviserMngServiceImpl implements AdviserMngService{
	@Autowired
	public AdviserMngDao adviserMngDao;
	 
	public Page getAdviser(int pageNum, int pageSize, String searchText, Integer proType, Long adviserId) {
		Page page = adviserMngDao.getAdvisers(pageNum, pageSize, searchText, proType, adviserId);
		return page;
	}

	 
	public boolean saveOrUpdateAdviser(AdviserVo vo, UserMngVo userMngVo) {
		/** 判断当前产品是执行添加还是修改操作 */
		boolean isAdd = vo.getId() == null ? true : false;
		if (isAdd){
			adviserMngDao.add(vo);
		}
		else {
			adviserMngDao.update(vo);
		}
		return false;
	}

 
	public AdviserVo queryAdviserById(Long id) {
		return adviserMngDao.queryAdviser(id);
	}

	 
	public List<AdviserVo> queryAll() {
		return adviserMngDao.queryAll();
	}

	 
	public Page getAdviserApplication(int pageNum, int pageSize, String searchText) {
		return adviserMngDao.getAdviserApplication(pageNum, pageSize, searchText);
	}

}
