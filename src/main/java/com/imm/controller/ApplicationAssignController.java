package com.imm.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imm.business.service.AdviserMngService;
import com.imm.business.service.CaseService;
import com.imm.business.vo.AdviserVo;
import com.imm.business.vo.CaseVo;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;
import com.imm.common.util.Page;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("AdviserMng/appass")
public class ApplicationAssignController extends BaseController {

	final static Log log = LogFactory.getLogger(ApplicationAssignController.class);
	@Autowired
	public AdviserMngService adviserMngService ;
	@Autowired
	public CaseService caseService;
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		return this.listByPage(request, response, 1, model);
	}

	@RequestMapping(value = "list/{pageNum}")
	public String listByPage(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("pageNum") Integer pageNum, ModelMap model) {

		pageNum = getPageNumber(pageNum);
		Integer pageSize = getPageSize(request);
		log.debug("param value: pageNum = " + pageNum + ", pageSize = " + pageSize);
		String searchText = request.getParameter("searchText");
		if (searchText == null)
			searchText = (String) model.get("searchText");
		model.addAttribute("searchText", searchText);

		 
		Page pageInfo = adviserMngService.getAdviserApplication(pageNum, pageSize, searchText );
		model.addAttribute("pageInfo", pageInfo);
		return "AdviserMng/appass/index";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return this.returnEdit(model,new AdviserVo());
	}

	@RequestMapping(value = "/edit/{adviserId}")
	public String edit(HttpServletRequest request, HttpServletResponse response, @PathVariable("adviserId") Long adviserId,
			ModelMap model) {
		log.debug("loading adviser id = " + adviserId);
		AdviserVo advo = new AdviserVo();
		String url = request.getParameter("url");
		if (url != null)
			try {
				model.addAttribute("url", URLEncoder.encode(url, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("" + e);
			}
		advo.setId(adviserId);
		return this.returnEdit(model, advo);
		
	}

	private String returnEdit(ModelMap model, AdviserVo advo) {
		if (null != advo.getId()) {
			advo = adviserMngService.queryAdviserById(advo.getId());
		}
		List<AdviserVo> AdviserVos = adviserMngService.queryAll();
		List<CaseVo> caseVos = caseService.queryAll();
		model.addAttribute("advisers", AdviserVos);
		model.addAttribute("clients", caseVos);
		return "AdviserMng/appass/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model, AdviserVo advise) {
	 
		UserMngVo userMngVo = getLoginUser();

		try {
			String birth = request.getParameter("birth");
			Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birth);
			;
			advise.setBirthday(birthday);
			
			boolean flag =adviserMngService.saveOrUpdateAdviser(advise, userMngVo); 

			log.info("save or update Product isSuccess : " + flag);

			if (request.getParameter("url") != null && request.getParameter("url").length() > 0) {
				String url = URLDecoder.decode(request.getParameter("url"), "UTF-8");
				JSONObject json = JSONObject.fromObject(url);
				model.addAttribute("pageSize", json.get("pageSize"));
				model.addAttribute("searchText", json.get("searchText"));
				return this.listByPage(request, response, Integer.parseInt(json.get("pageNum").toString()), model);
			} else
				return this.listByPage(request, response, 1, model);
		} catch (RuntimeException ex) {
			log.error("Error happened when saving product!", ex);
			model.addAttribute("errMsg", ex.getMessage());
			return this.returnEdit(model, advise);
		} catch (Exception ex) {
			log.error("Error happened when saving product!", ex);
			model.addAttribute("errMsg", "add error");
			return this.returnEdit(model, advise);
		}
	}

	@RequestMapping(value = "/delete/{prodId}", method = RequestMethod.GET)
	public String deleteProduct(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("prodId") Long prodId, ModelMap model) {
		log.info("delete getParameter prodId : " + prodId);
	 
		return this.listByPage(request, response, 1, model);
	}

	 
	@RequestMapping(value = "/show/{prodId}", method = RequestMethod.GET)
	public String showProduct(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("prodId") Long prdId, ModelMap model) {

		log.info("show getParameter prodId : " + prdId);
 
		return "prod/show";
	}

	@RequestMapping(value = "/isProductNameExist", method = RequestMethod.POST)
	private void isProductNameExist(HttpServletRequest request, HttpServletResponse response, String id, String name) {
		response.setContentType("text/html;charset=UTF-8");
		boolean flag = true;
	/*	ProductVo productVo = productService.getProductByName(name);
		if (null != productVo && !id.equals(productVo.getId() + "")) {
			flag = false;
		}*/
		printContentToResponse(response, flag);
	}
 
 

}
