package com.imm.common.util;

import java.io.Serializable;
import java.util.List;
 
public class Page implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 查询结果 */
    private List result;
    
    /** 分页信息Bean */
    private PageBean pageBean;
    
    /**
     * (空)
     */
    public Page() {}
    
    /**
     * 根据查询结果、分页信息构造
     * @param listResult 查询结果
     * @param pageBean 分页信息Bean
     */
    public Page(List listResult, PageBean pageBean) {
        this.result = listResult;
        this.pageBean = pageBean;
    }
    
    /**
     * 取得查询结果
     * @return 查询结果
     */
    public List getResult() {
        return result;
    }
    /**
     * 设置查询结果
     * @param lstResult 查询结果
     */
    public void setResult(List listResult) {
        this.result = listResult;
    }
    
    /**
     * 取得分页信息Bean
     * @return 分页信息Bean
     */
    public PageBean getPageBean() {
        return pageBean;
    }
    /**
     * 设置分页信息Bean
     * @param pageBean 分页信息Bean
     */
    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }
}