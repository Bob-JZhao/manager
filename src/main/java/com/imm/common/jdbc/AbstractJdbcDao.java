 package com.imm.common.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.StringUtils;

 
public abstract class AbstractJdbcDao {

	@Autowired
	private JdbcTemplate template;

	/**
	 * 通过SQL语句和ID找一条记录，并把记录封装成<code>com.jiunuo.common.jdbc.Row</code>返回.
	 *   其中ID可以为null，表示SQL语句中不需要参数.
	 * 
	 * @param sql
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Row findObject(String sql, Object id) {
		Map<String, Object> result = null;
		if (id == null) {
			result = template.queryForMap(sql);
		} else {
			result = template.queryForMap(sql, new Object[] { id });
		}
		Row rows = this.buildSingleRow(result);
		return rows;
	}

	/**
	 * 通过SQL语句和参数列表找一条记录，并把记录封装成<code>com.jiunuo.common.jdbc.Row</code>返回.
	 *   其中params可以为null，表示SQL语句中不需要参数.
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Row findObject(String sql, List<Object> params) {
		Map<String, Object> result = null;
		if (params == null) {
			result = template.queryForMap(sql);
		} else {
			result = template.queryForMap(sql, params.toArray());
		}
		Row rows = this.buildSingleRow(result);
		return rows;
	}
	
	
	/**
	 * 通过SQL语句和ID找一条记录，并把记录封装成指定的类对象返回.
	 *   其中ID可以为null，表示SQL语句中不需要参数.
	 *   在性能要求很高的时候请勿使用该方法，而应该使用不带clz参数的方法.
	 *   
	 * @param sql
	 * @param id
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Object findObject(String sql, Object id, Class clz) {
		Object result = null;
		ParameterizedBeanPropertyRowMapper rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(clz);
		if (id == null) {
			result = template.queryForObject(sql, rowMapper);
		} else {
			result = template.queryForObject(sql, new Object[] { id }, rowMapper);
		}
		return result;
	}
	
	/**
	 * 通过SQL语句和参数列表找一条记录，并把记录封装成指定的类对象返回.
	 *   其中params可以为null，表示SQL语句中不需要参数.
	 *   在性能要求很高的时候请勿使用该方法，而应该使用不带clz参数的方法.
	 * 
	 * @param sql
	 * @param params
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Object findObject(String sql, List<Object> params, Class clz) {
		Object result = null;
		ParameterizedBeanPropertyRowMapper rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(clz);
		if (params == null) {
			result = template.queryForObject(sql, rowMapper);
		} else {
			result = template.queryForObject(sql, params.toArray(), rowMapper);
		}
		return result;
	}
	
	/**
	 * 通过SQL语句和参数列表获取记录列表，并把记录封装成<code>com.jiunuo.common.jdbc.Row</code>的列表返回.
	 *   其中params可以为null，表示SQL语句中不需要参数.
	 * 
	 * @param sql
	 * @param params
	 * @return a List containing zero or more {@link Row} objects
	 */
	@SuppressWarnings("unchecked")
	protected List<Row> findList(String sql, List<Object> params) {
		List<Map<String, Object>> list = null;
		if (params == null) {
			list = template.queryForList(sql);
		} else {
			list = template.queryForList(sql, params.toArray());
		}
		List<Row> rows = buildRows(list);
		return rows;
	}
	
	/**
	 * 通过SQL语句和参数列表获取记录列表，并把记录封装成指定的类对象返回.
	 *   其中params可以为null，表示SQL语句中不需要参数.
	 *   在性能要求很高的时候请勿使用该方法，而应该使用不带clz参数的方法.
	 * 
	 * @param sql
	 * @param params
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List findList(String sql, List<Object> params, final Class clz) {
		ResultSetExtractor extractor = new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException {
				final List<Object> result = new ArrayList<Object>();
				int currentRow = 0;
				while (rs.next()) {
					result.add(new BeanPropertyRowMapper(clz).mapRow(rs, currentRow));
					currentRow++;
				}
				return result;
			}
		};
		return (List) findList(sql, params, extractor);
	}
	 
	/**
	 * 通过SQL语句和参数列表获取记录列表，并使用指定的结果处理类对结果集进行处理.
	 *   其中params可以为null，表示SQL语句中不需要参数.
	 * 
	 * @param sql
	 * @param params
	 * @param rse
	 * @return
	 */
	protected Object findList(String sql, List<Object> params, ResultSetExtractor rse) {
		if (params == null || params.isEmpty()) {
			return template.query(sql, rse);
		}
		Object[] args = params.toArray();
		return template.query(sql, args, rse);
	}
	
	/**
	 * 通过SQL语句和参数列表找记录数.
	 * 其中params可以为null，表示SQL语句中不需要参数.
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	protected long getCount(String sql, List<Object> params) {
		if (params == null || params.isEmpty()) {
			return template.queryForLong(sql);
		}
		return template.queryForLong(sql, params.toArray());
	}
	
	/**
	 * 执行插入、更新和删除操作.
	 * 返回插入、更新或删除的行数.
	 * 
	 * @param sql
	 * @return the number of rows affected
	 */
	protected int doUpdate(String sql) {
		return template.update(sql);
	}

	/**
	 * 执行带参数的插入、更新和删除操作.
	 * 返回插入、更新或删除的行数.
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	protected int doUpdate(String sql, List<Object> params) {
		if (params == null || params.isEmpty()) {
			return -1;
		}
		return template.update(sql, params.toArray());
	}
	
	/**
	 * 保存对象，并返回数据库自动生成的主键.
	 * @param sql 保存对象需要的insert语句
	 * @param params 保存对象需要的参数列表
	 * @return 数据库端自动生成的主键值.
	 */
	protected long saveObject(final String sql, final List<Object> params) {
		KeyHolder keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				if (params != null) {
					Iterator<Object> it = params.iterator();
					int index = 1;
					while (it.hasNext()) {
						ps.setObject(index, it.next());
						index++;
					}
				}
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}
	
	/**
	 * 执行批量插入、更新和删除操作.
	 * 返回每个操作影响到的记录行数.
	 * 
	 * @param sqlList
	 * @return
	 */
	protected int[] doBatchUpdate(List<String> sqlList) {
		if (sqlList == null || sqlList.isEmpty()) {
			return null;
		}
		return template
				.batchUpdate(sqlList.toArray(new String[sqlList.size()]));
	}
	
	/**
	 * 获取Spring JdbcTemplate对象，以便在业务层进行一些比较特殊的操作.
	 * @return an instance of <code>JdbcTemplate</code>.
	 */
	protected JdbcTemplate getTemplate() {
		return template;
	}
	
    /**
     * 根据结果集创建<code>com.jiunuo.common.jdbc.Row</code>对象.	
     * @param list a list of <code>Map</code>.
     * @return a list of <code>com.jiunuo.common.jdbc.Row</code>.
     */
	protected List<Row> buildRows(List<Map<String, Object>> list) {
		List<Row> rows = new ArrayList<Row>();
		if (list == null || list.isEmpty()) {
			return rows;
		}
		
		Iterator<Map<String, Object>> it = list.iterator();
		while (it.hasNext()) {
			Row r = buildSingleRow(it.next());
			rows.add(r);
		}
		return rows;
	}

	/**
	 * 根据结果集创建<code>com.jiunuo.common.jdbc.Row</code>对象.
	 * @param map a map for one table row, key is column name and value is the value of this column.
	 * @return an object of <code>com.jiunuo.common.jdbc.Row</code>
	 */
	protected Row buildSingleRow(Map<String, Object> map) {
		return new Row(map);
	}
	
	// 生成SQL语句需要的以逗号隔开的ID字符串
	protected String buildSqlIdString(Collection<? extends Object> idSet, boolean isLong) {
		final StringBuffer sb = new StringBuffer();
		if (isLong) {
			for (Object tmpId : idSet) {
				sb.append(tmpId + ", ");
			}
		} else {
			for (Object tmpId : idSet) {
				sb.append("'" + StringUtils.trimAllWhitespace(tmpId+"") + "', ");
			}
		}
		if (sb.length() > 0) {
			final int lastIndex = sb.lastIndexOf(",");
			sb.delete(lastIndex, sb.length());
		}
		if(sb.toString().length() == 0){
			sb.append("''");
		}
		return sb.toString();
	}
}