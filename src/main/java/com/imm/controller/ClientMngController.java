package com.imm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.imm.business.service.ClientService;
import com.imm.business.vo.ClientVo;
import com.imm.business.vo.mng.UserMngVo;
import com.imm.common.log.Log;
import com.imm.common.log.LogFactory;
import com.imm.common.util.Page;
import com.imm.common.util.SolrUtil;
import com.imm.common.util.property.PropertiesUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("ClientMng")
public class ClientMngController extends BaseController {

	final static Log log = LogFactory.getLogger(ClientMngController.class);

	@Autowired
	public ClientService clientService;

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

		Page pageInfo = clientService.getClient(pageNum, pageSize, searchText);
		model.addAttribute("pageInfo", pageInfo);
		return "ClientMng/index";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		/* return this.returnEdit(model, new ProductVo()); */
		return this.returnEdit(model, new ClientVo());
	}

	@RequestMapping(value = "/edit/{adviserId}")
	public String edit(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("adviserId") Long adviserId, ModelMap model) {
		log.debug("loading adviser id = " + adviserId);
		ClientVo advo = new ClientVo();
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

	private String returnEdit(ModelMap model, ClientVo advo) {
		if (null != advo.getId()) {
			advo = clientService.queryClientById(advo.getId());
		}
		model.addAttribute("ad", advo);
		return "ClientMng/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model, ClientVo vo) {

		UserMngVo userMngVo = getLoginUser();

		try {
			String sourcePath_per = vo.getPersonalInfo();
			log.info("sourcePath : " + sourcePath_per);
			int i = sourcePath_per.lastIndexOf(".");

			// 替换上传文件名称
			String destPath_per = PropertiesUtil.admin.personalInfo_url + "/" + vo.getClientName() + "/"
					+ vo.getClientName() + "" + sourcePath_per.substring(i, sourcePath_per.length());
			String path = PropertiesUtil.admin.personalInfo_url + "/" + vo.getClientName() + "/";
			String fileName = vo.getClientName() + "" + sourcePath_per.substring(i, sourcePath_per.length());
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			File destFile = new File(destPath_per);
			if (destFile.exists()) {
				destFile.delete();
			}
			Files.copy(new File(sourcePath_per).toPath(), new File(destPath_per).toPath());
			vo.setPersonalInfo(destPath_per);
			
			
			
			
			
			String sourcePath_back = vo.getBackgroundInfo();
			log.info("sourcePath_back : " + sourcePath_back);
			int k = sourcePath_back.lastIndexOf(".");

			// 替换上传文件名称
			String destPath_back = PropertiesUtil.admin.backGround_url + "/" + vo.getClientName() + "/"
					+ vo.getClientName() + "" + sourcePath_back.substring(k, sourcePath_back.length());
			String path_back = PropertiesUtil.admin.backGround_url + "/" + vo.getClientName() + "/";
			String fileName_back = vo.getClientName() + "" + sourcePath_back.substring(k, sourcePath_back.length());
			File targetFile_back = new File(path_back, fileName_back);
			if (!targetFile_back.exists()) {
				targetFile_back.mkdirs();
			}
			File destFile_back = new File(destPath_back);
			if (destFile_back.exists()) {
				destFile_back.delete();
			}
			Files.copy(new File(sourcePath_back).toPath(), new File(destPath_back).toPath());
			vo.setBackgroundInfo(destPath_back);
			
			
			boolean flag = clientService.saveOrUpdateClient(vo, userMngVo);
			if (flag) {
				SolrUtil.indexFilesSolrCell(vo.getPersonalInfo(), vo.getPersonalInfo(),"word");
				SolrUtil.indexFilesSolrCell(vo.getBackgroundInfo(), vo.getBackgroundInfo(),"pdf");
			}
			log.info("save or update client isSuccess : " + flag);

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
			return this.returnEdit(model, vo);
		} catch (Exception ex) {
			log.error("Error happened when saving product!", ex);
			model.addAttribute("errMsg", "add error");
			return this.returnEdit(model, vo);
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

	@RequestMapping(value = "uploadPersonalFile")
	public void uploadFile(@RequestParam(value = "personalinfofile", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String dir = request.getParameter("dir");
		if (StringUtils.isEmpty(dir)) {
			dir = "providerLogo";
		}
		String path = PropertiesUtil.admin.personalInfo_url_temp + "/";
		log.info("uploadProvLogo path : " + path);
		String fileName = file.getOriginalFilename();
		int i = fileName.lastIndexOf(".");

		// 替换上传文件名称
		fileName = System.currentTimeMillis() + "" + fileName.substring(i, fileName.length());
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		/** 最终显示的图片地址 */
		String personalInfoPath = path + fileName;
		log.info("temp Url : " + personalInfoPath);
		try {
			file.transferTo(targetFile);
			response.getWriter().print(personalInfoPath);
		} catch (Exception e) {
			log.warn("uploadProvLogo Error happened.", e);
		}
	}
	@RequestMapping(value = "uploadBackgroundFile")
	public void uploadBackgroundFile(@RequestParam(value = "backgroundinfofile", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		 
		String path = PropertiesUtil.admin.backGround_url_temp + "/";
		log.info("uploadBackgroundFile path : " + path);
		String fileName = file.getOriginalFilename();
		int i = fileName.lastIndexOf(".");

		// 替换上传文件名称
		fileName = System.currentTimeMillis() + "" + fileName.substring(i, fileName.length());
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		/** 最终显示的图片地址 */
		String personalInfoPath = path + fileName;
		log.info("temp Url : " + personalInfoPath);
		try {
			file.transferTo(targetFile);
			response.getWriter().print(personalInfoPath);
		} catch (Exception e) {
			log.warn("uploadProvLogo Error happened.", e);
		}
	}
	@RequestMapping(value = "down")
	public void down(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		String filepath = request.getParameter("filePath");
		int i = filepath.lastIndexOf("/");
		// 替换上传文件名称
		String fileName = filepath.substring(i, filepath.length()).replace("/", "");
		// 得到要下载的文件
		File file = new File(filepath);
		// 如果文件不存在
		if (!file.exists()) {
			request.setAttribute("message", "file not exist！！");
			return;
		}
		// 处理文件名
		// 设置响应头，控制浏览器下载该文件
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		// 读取要下载的文件，保存到文件输入流
		FileInputStream in = new FileInputStream(filepath);
		// 创建输出流
		OutputStream out = response.getOutputStream();
		// 创建缓冲区
		byte buffer[] = new byte[1024];
		int len = 0;
		// 循环将输入流中的内容读取到缓冲区当中
		while ((len = in.read(buffer)) > 0) {
			// 输出缓冲区的内容到浏览器，实现文件下载
			out.write(buffer, 0, len);
		}
		// 关闭文件输入流
		in.close();
		// 关闭输出流
		out.close();
	}

	@RequestMapping(value = "doc", method = RequestMethod.GET)
	public String docSearch(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String keyword = request.getParameter("keyword");
		List<String> queryRes = SolrUtil.solrQuery(keyword);
		if (CollectionUtils.isNotEmpty(queryRes)) {
			Page pageInfo = clientService.getClientSearch(1, 15, queryRes);
			model.addAttribute("pageInfo", pageInfo);
		}
		model.addAttribute("keyword", keyword);
		return "ClientMng/index";

	}
}
