package com.imm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imm.business.service.UserMngService;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.encoder.Md5PwdEncoder;
import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;
import com.imm.common.util.DateUtil;

@Controller
@RequestMapping("welcome")
public class MainController extends BaseController {

	@Autowired
	private UserMngService userMngService;
	
	final static Log log = LogFactory.getLogger(MainController.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String welcome(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		UserDetails userDetails = null;
		try {
			/** Spring 默认失效之后返回 anonymousUser ， 不能转换为detail，所以抛异常之后就跳转到登录*/
			userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
	        return "login/index";
		}
		String currDate = DateUtil.getDateYYYYMMDDHHMMSS(DateUtil.getNowDate());
    	log.info("用户名是【" + userDetails.getUsername() + "】的用户在【" + currDate + "】时间执行了后台登录操作");
		model.addAttribute("userName", userDetails.getUsername());
		return "welcome/index";
	}
	
	@RequestMapping(value = "/editpwd", method = RequestMethod.GET)
	public String editpwd() {
		return "common/editPwd";
	}
	
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(ModelMap model , HttpServletResponse response, String oldPassword , String newPassword , String confirmPassword){
		
		model.addAttribute("oldPassword", oldPassword);
		model.addAttribute("confirmPassword", confirmPassword);
		model.addAttribute("newPassword", newPassword);
		
		String errMsg = "";
		UserDetails userDetails = null;
		try {
			/** Spring 默认失效之后返回 anonymousUser ， 不能转换为detail，所以抛异常之后就跳转到登录*/
			userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			log.error("登录失效，manage welcome submit exception : " + e);
			return "login/index";
		}
		
		UserMngVo userMngVo = userMngService.getUserByLoginId(userDetails.getUsername());
		if(null == userMngVo){
			log.warn("获取后台登录用户失败 。" + userDetails.getUsername());
			return "login/index";
		}
		
		String md5OldPassword = Md5PwdEncoder.encodePassword(oldPassword);
		if(!md5OldPassword.equals(userMngVo.getPassword())){
			errMsg = "原密码输入有误";
			model.addAttribute("errMsg", errMsg);
			return "common/editPwd";
		}
		
		if(StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(confirmPassword)){
			errMsg = "请输入新密码或确认密码";
			model.addAttribute("errMsg", errMsg);
			return "common/editPwd";
		}
		if(!newPassword.equals(confirmPassword)){
			errMsg = "新密码和确认密码不一样";
			model.addAttribute("errMsg", errMsg);
			return "common/editPwd";
		}
		
		userMngVo.setPassword(Md5PwdEncoder.encodePassword(newPassword));
		boolean flag = userMngService.updatePass(userMngVo);
		if(!flag){
			log.info(userMngVo.getLoginId() + " 后台修改密码失败 。 ");
			model.addAttribute("errMsg", "修改失败");
			return "common/editPwd";
		}
		
		try {
			response.sendRedirect("/manage/login/logout");
		} catch (Exception ex) {
			log.error("后台修改密码跳转页面" + ex);
		}
		return null;
	}
}