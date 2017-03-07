package com.imm.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imm.business.service.MessageService;
import com.imm.business.vo.MessageVo;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;
import com.imm.common.util.Page;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("Message")
public class MessageMngController extends BaseController {

	final static Log log = LogFactory.getLogger(MessageMngController.class);
	@Autowired
	public MessageService messageService;

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
		UserMngVo user = getLoginUser();
		Page pageInfo = messageService.getMessage(pageNum, pageSize, searchText,user.getId());
		model.addAttribute("pageInfo", pageInfo);
		return "Message/index";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		/* return this.returnEdit(model, new ProductVo()); */
		return this.returnEdit(model, new MessageVo());
	}

	@RequestMapping(value = "/edit/{adviserId}")
	public String edit(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("adviserId") Long adviserId, ModelMap model) {
		log.debug("loading adviser id = " + adviserId);
		MessageVo advo = new MessageVo();
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

	private String returnEdit(ModelMap model, MessageVo advo) {
		if (null != advo.getId()) {
			advo = messageService.queryMessageById(advo.getId());
		}
		List<UserMngVo> users = messageService.getAllUser();
		model.addAttribute("to", users);
		model.addAttribute("ad", advo);
		return "Message/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model, MessageVo mess) {

		UserMngVo userMngVo = getLoginUser();

		try {
			mess.setFromId(userMngVo.getId());
			boolean flag = messageService.saveOrUpdateMessage(mess, userMngVo);

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
			return this.returnEdit(model, mess);
		} catch (Exception ex) {
			log.error("Error happened when saving product!", ex);
			model.addAttribute("errMsg", "add error");
			return this.returnEdit(model, mess);
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
		 
		MessageVo messageVo = messageService.queryMessageById(prdId);
		messageService.saveOrUpdateMessage(messageVo, null); // 设置为已读
		model.addAttribute("messageVo", messageVo); 
		return "Message/show";
	}

	@RequestMapping(value = "/isProductNameExist", method = RequestMethod.POST)
	private void isProductNameExist(HttpServletRequest request, HttpServletResponse response, String id, String name) {
		response.setContentType("text/html;charset=UTF-8");
		boolean flag = true;
		/*
		 * ProductVo productVo = productService.getProductByName(name); if (null
		 * != productVo && !id.equals(productVo.getId() + "")) { flag = false; }
		 */
		printContentToResponse(response, flag);
	}

}
