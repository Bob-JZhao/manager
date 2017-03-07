package com.imm.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imm.business.service.UserMngService;
import com.imm.business.service.role.MngUserRoleService;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.business.vo.role.MngUserRoleVo;
import com.imm.common.encoder.Md5PwdEncoder;
import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;
import com.imm.common.util.Page;


 
@Controller
@RequestMapping("usr")
public class UserManageController extends BaseController {

	@Autowired
	private UserMngService userMngService;
	
	@Autowired
	private MngUserRoleService mngUserRoleService;
	
	final static Log log = LogFactory.getLogger(UserManageController.class);
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		//check if session is not empty then makes it invalidated
		/*if(request.getSession(false) != null){
			request.getSession(false).invalidate();
		}*/
		return this.listByPage(request, response, 1, model);
	}
	
	@RequestMapping(value = "list/{pageNum}")
	public String listByPage(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("pageNum") Integer pageNum, ModelMap model) {
		
		pageNum = getPageNumber(pageNum);
		Integer pageSize = getPageSize(request);
		String searchText = getSearchText(request, model);
		log.debug("Param value: pageNum = " + pageNum + ", pageSize = " + pageSize + ", searchText = " + searchText);
		
		Page pageInfo = userMngService.getUsers(pageNum, pageSize, searchText);		
		model.addAttribute("pageInfo", pageInfo);
		
		return "user/index";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(HttpServletRequest request, HttpServletResponse response, 
			UserMngVo userMngVo, ModelMap model){		
		log.debug("adding new manager");
		if (userMngVo.equals(null)){
			UserMngVo newUserMngVo = new UserMngVo();
			model.addAttribute("userMngVo", newUserMngVo);
		}else {
			model.addAttribute("userMngVo", userMngVo);		
		}
		return "user/edit";
	}
	
	@RequestMapping(value = "/edit/{userMngId}", method = RequestMethod.GET)
	public String edit(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userMngId") Long userMngId, ModelMap model){		
		log.debug("loading manager:: id = " + userMngId);		
		UserMngVo userMngVo = userMngService.getUserById(userMngId);
		List<MngUserRoleVo> roles = mngUserRoleService.getMngUserRoleByUserId(userMngId);
		if(roles != null && !roles.isEmpty()){
			for(MngUserRoleVo role : roles){
				if(role.getRoleId() == 1){
					model.addAttribute("ADMIN", true);
				}else if(role.getRoleId() == 2){
					model.addAttribute("OPERATE", true);
				}else if(role.getRoleId() == 3){
					model.addAttribute("FINAN", true);
				}else if(role.getRoleId() == 4){
					model.addAttribute("MARKET", true);
				}else if(role.getRoleId() == 5){
					model.addAttribute("SERVICE", true);
				}else if(role.getRoleId() == 6){
					model.addAttribute("TECH", true);
				}
			}
		}

		model.addAttribute("userMngVo", userMngVo);		
		return "user/edit";
	}
	
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, HttpServletResponse response,
			UserMngVo userMngVo, ModelMap model){
		
		log.debug("Saving the manager :: id = " + userMngVo.getId());
		
		// check if login id of manager invalidate 
		if (!validateUserMngLoginId(model, userMngVo.getId(),userMngVo.getLoginId())){
			model.addAttribute("userMngVo", userMngVo);
			return this.add(request, response, userMngVo, model);
		}
		
		if (userMngVo.getCreateTime() == null){
			Date currentTime = new Date();
			userMngVo.setCreateTime(currentTime);
		}
		
		if(userMngVo.getRoles() == null || userMngVo.getRoles().isEmpty()){
			model.addAttribute("errMsg", "请选择至少一个用户角色");
			if(userMngVo.getId() == null){
				return this.add(request, response, userMngVo, model);
			}else{
				return this.edit(request, response, userMngVo.getId(), model);
			}
			
		}
		
		if(userMngVo.getPassword().length() <= 12){
			userMngVo.setPassword(Md5PwdEncoder.encodePassword(userMngVo.getPassword()));
		}
		
		userMngService.addorEditUser(userMngVo);
		
		
		model.addAttribute("userMngVo", userMngVo);
		return this.listByPage(request, response, 1, model);		
	}

	
	// check if it is duplicate for provider name
	@RequestMapping(value = "/verifyLoginId", method = RequestMethod.POST)
	public void verifyName(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		response.setContentType("text/html;charset=UTF-8");
		String userMngIdStr = request.getParameter("id");
		String userMngLoginId = request.getParameter("loginId");
		Long userMngId = null;
		if (StringUtils.isNotEmpty(userMngIdStr)) {
			userMngId = Long.valueOf(userMngIdStr.trim());
		}
		boolean flag = validateUserMngLoginId(model, userMngId, userMngLoginId);
		printContentToResponse(response, flag);
	}
	
	public boolean validateUserMngLoginId(ModelMap model, Long id, String loginId){	
		String validateMessage = "";
		UserMngVo userMngVo = userMngService.getUserByLoginId(loginId);
		if(null != userMngVo){
			if(userMngVo.getId() != id){
				validateMessage = "用户登录ID已经存在";
				model.addAttribute("errMsg", validateMessage);
				return false;
			}
		}
		return true;
	}
	
		
	@RequestMapping(value = "/detail/{userMngId}", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("userMngId") Long userMngId, ModelMap model) {		
		log.debug("Show the detail of the manager :: id = " + userMngId);		
		UserMngVo userMngVo = userMngService.getUserById(userMngId);
		List<MngUserRoleVo> roles = mngUserRoleService.getMngUserRoleByUserId(userMngId);
		if(roles != null && !roles.isEmpty()){
			for(MngUserRoleVo role : roles){
				if(role.getRoleId() == 1){
					model.addAttribute("ADMIN", true);
				}else if(role.getRoleId() == 2){
					model.addAttribute("OPERATE", true);
				}else if(role.getRoleId() == 3){
					model.addAttribute("FINAN", true);
				}else if(role.getRoleId() == 4){
					model.addAttribute("MARKET", true);
				}else if(role.getRoleId() == 5){
					model.addAttribute("SERVICE", true);
				}else if(role.getRoleId() == 6){
					model.addAttribute("TECH", true);
				}
			}
		}
		model.addAttribute("userMngVo", userMngVo);		
		return "user/detail";
	}
	
	@RequestMapping(value = "/delete/{userMngId}", method = RequestMethod.GET)
	public String delete(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userMngId") Long userMngId, ModelMap model){			
		log.debug("delete the manage user :: id = " + userMngId);		
		userMngService.deleteUser(userMngId);
		return this.listByPage(request, response, 1, model);
	}
	
}
