package com.imm.common.util;

import java.io.Serializable;

 
public class PageBean implements Serializable  {
    
	private static final long serialVersionUID = 1L;

	/**缺省的分页条数*/
    private static int DEF_PAGE_VIEW_SIZE = 25;
    
    /** 当前页 */
    private int pageIndex;
    
    /** 当前页显示记录条数 */
    private int pageSize;
    
    /** 取得查询总记录数 */
    private int count;

    /**
     * (空)
     */
    public PageBean() {}
    
    /**
     * 根据当前显示页与每页显示记录数设置查询信息初始对象
     * @param page 当前显示页号
     * @param pageSize 当前页显示记录条数
     */
    public PageBean(int pageIndex, int pageSize) {
        this.pageIndex = (pageIndex <= 0) ? 1 : pageIndex;
        this.pageSize = (pageSize <= 0) ? DEF_PAGE_VIEW_SIZE : pageSize;
    }

    /**
     * 取得当前显示页号
     * @return 当前显示页号
     */
    public int getPageIndex() {
        return (pageIndex <= 0) ? 1 : pageIndex;
    }
    /**
     * 设置当前页
     * @param page 当前页
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
    
    /**
     * 取得当前显示页号最多显示条数
     * @return 当前显示页号最多显示条数
     */
    public int getPageSize() {
        return (pageSize <= 0) ? DEF_PAGE_VIEW_SIZE : pageSize;
    }
    /**
     * 设置当前页显示记录条数
     * @param pageSize 当前页显示记录条数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * 取得查询取得记录总数
     * @return 取得查询取得记录总数
     */
    public long getCount() {
        return count;
    }
    /**
     * 设置查询取得记录总数
     * @param count 查询取得记录总数
     */
    public void setCount(int count) {
        
        this.count = (count < 0) ? 0 : count;

        if (this.count == 0) {
            this.pageIndex = 0;
        }
        
    }
    
    /**
     * 取得当前查询总页数
     * @return 当前查询总页数
     */
    public int getPages() {
        return (count + getPageSize() - 1) / getPageSize();
    }
    /**
     * 取得起始显示记录号
     * @return 起始显示记录号
     */
    public int getStartNo() {
        return ((getPageIndex() - 1) * getPageSize() + 1);
    }
    
    /**
     * 取得结束显示记录号
     * @return 结束显示记录号
     */
    public int getEndNo() {
        return Math.min(getPageIndex() * getPageSize(), count);
    }
    
    /**
     * 取得前一显示页码
     * @return 前一显示页码
     */
    public int getPrePageNo() {
        return Math.max(getPageIndex() - 1, 1);
    }
    
    /**
     * 取得后一显示页码
     * @return 后一显示页码
     */
    public int getNextPageNo() {
        return Math.min(getPageIndex() + 1, getPages());
    }
}