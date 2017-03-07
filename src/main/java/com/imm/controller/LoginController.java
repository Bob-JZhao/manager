package com.imm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;

@Controller
public class LoginController {

	final static Log log = LogFactory.getLogger(LoginController.class);

	 /**
     * 登录入口
     */
    @RequestMapping(value = "/login/{error}" , method = RequestMethod.GET)
    public String login(@PathVariable String error,ModelMap model
    		,HttpServletRequest request) {
    	
    	String loginMessage = checkLoginResult(error);
    	
        model.addAttribute("loginMessage", loginMessage);
        
        return "login/index";
        
    }
    
    private String checkLoginResult(String error){
    	String loginMessage = "";
    	if(null!=error && error.equals("timeout")){
    		loginMessage = "time out ";
    	}else if(null!=error && error.equals("validateFail")){
    		loginMessage = "validate user or password fail ";
    	}else if(null!=error && error.equals("logout")){
    		loginMessage = "logout success";
    	}else if(null!=error && error.equals("singleton")){
    		loginMessage = "你的帐号已登录，不允许重复登陆";	
    	}else{}
    	return loginMessage;
    }
    
    /**
     * 没有权限访问跳转url
     */
    @RequestMapping(value="/denied")
    public String denied(){
        log.info("denied......");
        return "login/denied";
    }
}