 
package com.imm.common.jdbc;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * An entry mapped from a database record.
 * @author bob zhao
 */
public class Row {
	private Map<String, Object> data = null;

	/** 返回数据Map对象，FreeMarker页面显示用 */
	public Map<String, Object> getData() {
		return data;
	}

	protected Row(Map<String, Object> columnData) {
		this.data = new HashMap<String, Object>();
		if (columnData != null && !columnData.isEmpty()) {
			Set<Map.Entry<String, Object>> entries = columnData.entrySet();
			for (Iterator<Map.Entry<String, Object>> it = entries.iterator(); it.hasNext();) {
				Map.Entry<String, Object> entry = it.next();
				data.put(entry.getKey().toUpperCase(), entry.getValue());
			}
		}
	}

	public String getString(String colName) {
		return (String) this.getObject(colName);
	}

	public Date getDate(String colName) {
		return (Date) this.getObject(colName);
	}

	public Time getTime(String colName) {
		return (Time) this.getObject(colName);
	}

	public Timestamp getTimestamp(String colName) {
		return (Timestamp) this.getObject(colName);
	}

	public long getLong(String colName) {
		return getBigDecimal(colName).longValue();
	}

	public int getInt(String colName) {
		return getNumber(colName).intValue();
	}

	public double getDouble(String colName) {
		return getNumber(colName).doubleValue();
	}

	public float getFloat(String colName) {
		if (getNumber(colName) != null) {
			return getNumber(colName).floatValue();
		} else {
			return 0;
		}
	}

	protected BigDecimal getBigDecimal(Number n) {
		BigDecimal bd = null;
		if (n instanceof BigDecimal) {
			bd = (BigDecimal) n;
		} else {
			bd = new BigDecimal(n.doubleValue());
		}
		return bd;
	}

	public BigDecimal getBigDecimal(String colName) {
		Number n = this.getNumber(colName);
		return getBigDecimal(n);
	}

	public boolean getBoolean(String colName) {
		return ((Boolean) this.getObject(colName)).booleanValue();
	}

	public Blob getBlob(String colName) {
		return ((Blob) this.getObject(colName));
	}

	public Object getObject(String colName) {
		return this.data.get(colName.toUpperCase());
	}

	public Number getNumber(String colName) {
		Number n = (Number) this.getObject(colName);
		return n;
	}

	public int getColumnCount() {
		return this.data.size();
	}

	public Set<String> getColumnNames() {
		return this.data.keySet();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ColumnCount=");
		sb.append(getColumnCount());
		sb.append(" [ ");
		Set<String> colNames = getColumnNames();
		for (String name : colNames) {
			sb.append(name);
			sb.append(" ");
		}
		sb.append("]");
		return sb.toString();
	}
}
