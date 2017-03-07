package com.imm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imm.business.vo.AdviserVo;
import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;
import com.imm.common.util.Page;

@Controller
@RequestMapping("prd")
public class ProductController extends BaseController {

	final static Log log = LogFactory.getLogger(ProductController.class);

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

		String proTypeStr = request.getParameter("prdproType");
		if (proTypeStr == null)
			proTypeStr = (String) model.get("prdproType");
		String prdStatusStr = request.getParameter("prdprvStatus");
		if (prdStatusStr == null)
			prdStatusStr = (String) model.get("prdprvStatus");
		String operationTypeStr = request.getParameter("prdOperationType");
		if (operationTypeStr == null)
			operationTypeStr = (String) model.get("prdOperationType");
		String prvIdStr = request.getParameter("prdprvId");
		if (prvIdStr == null)
			prvIdStr = (String) model.get("prdprvId");
		Integer proType = null;
		if (proTypeStr != null && proTypeStr.length() > 0)
			proType = Integer.parseInt(proTypeStr);
		Integer prdStatus = null;
		if (prdStatusStr != null && prdStatusStr.length() > 0)
			prdStatus = Integer.parseInt(prdStatusStr);
		Integer operationType = null;
		if (operationTypeStr != null && operationTypeStr.length() > 0)
			operationType = Integer.parseInt(operationTypeStr);
		Long prvId = null;
		if (prvIdStr != null && prvIdStr.length() > 0)
			prvId = Long.parseLong(prvIdStr);
		model.addAttribute("prdproType", proType);
		model.addAttribute("prdprvStatus", prdStatus);
		model.addAttribute("prdOperationType", operationType);
		model.addAttribute("prdprvId", prvId);
		/*Page pageInfo = productService.getAdminProducts(pageNum, pageSize, searchText, proType, prvId, operationType,
				prdStatus);*/
		Page pageInfo = new Page();
		model.addAttribute("pageInfo", pageInfo);
		return "prod/index";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
	/*	return this.returnEdit(model, new ProductVo());*/
		return this.returnEdit(model,new AdviserVo());
	}

	@RequestMapping(value = "/edit/{prodId}")
	public String edit(HttpServletRequest request, HttpServletResponse response, @PathVariable("prodId") Long prodId,
			ModelMap model) {
		log.debug("loading product:: id = " + prodId);
	 
		return "";
		
	}

	private String returnEdit(ModelMap model, AdviserVo product) {
 
		return "prod/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model, AdviserVo advise) {

		 return "";
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
	 
		printContentToResponse(response, flag);
	}
 
 

}
