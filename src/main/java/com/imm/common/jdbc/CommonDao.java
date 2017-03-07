package com.imm.common.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.imm.common.util.Page;
import com.imm.common.util.PageBean;
 
@Component("commonDao")
public class CommonDao extends AbstractJdbcDao {

	/**
	 * 分页查询接口.
	 * 
	 * @param sql
	 * @param pageNum
	 * @param pagesize
	 * @param params
	 * @return
	 */
	public Page getPage(String sql, int pageNum, int pagesize, List<Object> params) {
		return getPage(sql, pageNum, pagesize, -1L, params);
	}

	/**
	 * 分页查询接口，并把结果转化为指定的类类型. 该方法是为了方便，而不是为了性能；如果您的查询要求高性能，请使用不带类类型的查询方法。
	 * 
	 * @param sql
	 * @param pageNum
	 * @param pagesize
	 * @param params
	 * @param clz
	 *            结果集中的对象类型
	 * @return
	 */
	public Page getPage(String sql, int pageNum, int pagesize, List<Object> params, Class clz) {
		return getPage(sql, pageNum, pagesize, -1L, params, clz);
	}

	/**
	 * 已知Count的分页查询.
	 * 
	 * @param sql
	 *            要查询的SQL语句
	 * @param pageNum
	 * @param pagesize
	 * @param count
	 * @param params
	 * @return
	 */
	public Page getPage(String sql, int pageNum, int pagesize, Long count, List<Object> params) {
		return getPage(sql, pageNum, pagesize, count, params, null);
	}

	/**
	 * 分页查询接口，并把结果转化为指定的类类型. 该方法是为了方便，而不是为了性能；如果您的查询要求高性能，请使用不带类类型的查询方法。
	 * 
	 * @param sql
	 * @param pageNum
	 * @param pagesize
	 * @param count
	 * @param params
	 * @param clz
	 * @return
	 */
	public Page getPage(String sql, int pageNum, int pagesize, Long count, List<Object> params, Class clz) {
		PageBean pageBean = new PageBean(pageNum, pagesize);

		// 取得本次查询返回的记录数
		Long cnt = count;
		if (cnt == null || cnt.longValue() < 0) {
			cnt = getResultCnt(sql, params);
		}
		pageBean.setCount(cnt.intValue());

		if (pageBean.getCount() == 0) {
			return new Page(new ArrayList(), pageBean);
		}

		// 取得本次查询返回结果集
		final int startRow = pageBean.getStartNo() - 1;
		ResultSetExtractor extractor = null;
		if (clz != null) {
			extractor = new PaginationResultSetBeanExtractor(startRow, pageBean.getPageSize(), clz);
		} else {
			extractor = new PaginationResultSetExtractor(startRow, pageBean.getPageSize());
		}
		List<Object> result = (List) findList(sql, params, extractor);
		return new Page(result, pageBean);
	}

	/**
	 * 获取该SQL返回的记录条数.
	 * 
	 * @param hsql
	 *            hql语句
	 * @param params
	 *            查询参数
	 * @return 总条数
	 * @throws AppException
	 */
	private Long getResultCnt(String sql, List<Object> params) {

		// 删除查询语句中的order by子句
		String newSql = delOrderbySQL(sql);

		// 修改SQL语句
		int idx = newSql.toUpperCase().indexOf("FROM ");
		newSql = "select count(*) " + newSql.substring(idx);
		return new Long(getCount(newSql, params));
	}

	/**
	 * 删除查询条件中的order by子句
	 * 
	 * @param queryString
	 *            查询SQL语句
	 * @return 删除查询语句中的order by子句后的查询语句
	 */
	private String delOrderbySQL(String queryString) {

		String result = queryString;

		int idx = queryString.indexOf("order by");

		if (idx > 0) {
			result = queryString.substring(0, idx);
		}
		return result;
	}

	 
	private class PaginationResultSetExtractor implements ResultSetExtractor {
		int startRow;
		int pageSize;

		public PaginationResultSetExtractor(int startRow, int pageSize) {
			this.startRow = startRow;
			this.pageSize = pageSize;
		}

		public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			int currentRow = 0;
			while (rs.next() && currentRow < startRow + pageSize) {
				if (currentRow >= startRow) {
					result.add((Map) new ColumnMapRowMapper().mapRow(rs, currentRow));
				}
				currentRow++;
			}
			List<Row> rows = buildRows(result);
			return rows;
		}
	}

	 
	private class PaginationResultSetBeanExtractor extends PaginationResultSetExtractor {
		Class targetClass = null;

		public PaginationResultSetBeanExtractor(int startRow, int pageSize, Class clz) {
			super(startRow, pageSize);
			this.targetClass = clz;
		}

		public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			final List<Object> result = new ArrayList<Object>();
			int currentRow = 0;
			while (rs.next() && currentRow < startRow + pageSize) {
				if (currentRow >= startRow) {
					result.add(new BeanPropertyRowMapper(targetClass).mapRow(rs, currentRow));
				}
				currentRow++;
			}
			return result;
		}
	}

	public Page getPageWithLimit(String sql, int pageNum, int pagesize, List<Object> params, Class clz) {
		PageBean pageBean = new PageBean();
		Page page = new Page();
		if(pageNum == 0)
			pageNum = 1;
		String limit = " limit " + (pageNum - 1) * pagesize + "," + pagesize;
		long count = this.getCount("select count(1) from (" + sql + ") as aaa", params);
		List<Object> list = new ArrayList<Object>();
		if(clz!=null)
			list =  this.findList(sql + limit, params, clz);
		else 
			list = (List)this.findList(sql + limit, params);
		page.setResult(list);
		pageBean.setCount((int) count);
		pageBean.setPageIndex(pageNum);
		pageBean.setPageSize(pagesize);
		page.setPageBean(pageBean);
		return page;
	}
}
