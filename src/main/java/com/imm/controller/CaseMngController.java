package com.imm.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imm.business.service.CaseService;
import com.imm.business.vo.CaseVo;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;
import com.imm.common.util.Page;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("CaseMng")
public class CaseMngController extends BaseController {

	final static Log log = LogFactory.getLogger(CaseMngController.class);
	@Autowired
	public CaseService caseService ;
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

		 
		Page pageInfo = caseService.getCase(pageNum, pageSize, searchText);
		model.addAttribute("pageInfo", pageInfo);
		return "CaseMng/index";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
	/*	return this.returnEdit(model, new ProductVo());*/
		return this.returnEdit(model,new CaseVo());
	}

	@RequestMapping(value = "/edit/{caseId}")
	public String edit(HttpServletRequest request, HttpServletResponse response, @PathVariable("caseId") Long caseId,
			ModelMap model) {
		log.debug("loading adviser id = " + caseId);
		CaseVo advo = new CaseVo();
		String url = request.getParameter("url");
		if (url != null)
			try {
				model.addAttribute("url", URLEncoder.encode(url, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("" + e);
			}
		advo.setId(caseId);
		return this.returnEdit(model, advo);
		
	}

	private String returnEdit(ModelMap model, CaseVo advo) {
		if (null != advo.getId()) {
			advo = caseService.queryCaseById(advo.getId());
		}
		model.addAttribute("ad", advo);
		return "CaseMng/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model, CaseVo caseVo) {
	 
		UserMngVo userMngVo = getLoginUser();

		try {
			/*String birth = request.getParameter("birth");
			Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birth);
			;
			a.setBirthday(birthday);*/
			
			boolean flag = caseService.saveOrUpdateCase(caseVo , userMngVo); 

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
			return this.returnEdit(model, caseVo);
		} catch (Exception ex) {
			log.error("Error happened when saving product!", ex);
			model.addAttribute("errMsg", "add error");
			return this.returnEdit(model, caseVo);
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
	
	@RequestMapping(value = "/recentSign/list", method = RequestMethod.GET)
	public String listR(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		return this.listByPageRec(request, response, 1, model);
	}
	@RequestMapping(value = "list/{pageNum}")
	public String listByPageRec(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("pageNum") Integer pageNum, ModelMap model) {

		pageNum = getPageNumber(pageNum);
		Integer pageSize = getPageSize(request);
		log.debug("param value: pageNum = " + pageNum + ", pageSize = " + pageSize);
		String searchText = request.getParameter("searchText");
		if (searchText == null)
			searchText = (String) model.get("searchText");
		model.addAttribute("searchText", searchText);

		 
		Page pageInfo = caseService.getCase(pageNum, pageSize, searchText);
		model.addAttribute("pageInfo", pageInfo);
		return "CaseMng/recentSign/index";
	}

}
