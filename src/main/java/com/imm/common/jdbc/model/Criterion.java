package com.imm.common.jdbc.model;

/// <summary>
/// 查询规范
/// </summary>
public class Criterion {
	private String propertyName;
	private Object value;
	private Object[] arrValue;
	private CriteriaOperator operator = CriteriaOperator.Equal;
	private QueryOperator queryOperator = QueryOperator.And;

	// / <summary>
	// / 构造函数
	// / </summary>
	public Criterion() {
	}

	// / <summary>
	// / 初始化查询规范,并传入属性名 , 查询运算符 , 值
	// / </summary>
	// / <param name="propertyName"></param>
	// / <param name="operator"></param>
	// / <param name="value"></param>
	public Criterion(String propertyName, CriteriaOperator operator, Object value) {
		setPropertyName(propertyName);
		setValue(value);
		setOperator(operator);
	}

	public Criterion(String propertyName, Object value) {
		setPropertyName(propertyName);
		setValue(value);
	}

	public Criterion(String propertyName, CriteriaOperator operator, Object[] arrValue) {
		setPropertyName(propertyName);
		setArrValue(arrValue);
		setOperator(operator);
	}

	public Criterion(String propertyName, CriteriaOperator operator, Object value, QueryOperator queryOperator) {
		setPropertyName(propertyName);
		setValue(value);
		setOperator(operator);
		setQueryOperator(queryOperator);
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object[] getArrValue() {
		return arrValue;
	}

	public void setArrValue(Object[] arrValue) {
		this.arrValue = arrValue;
	}

	public CriteriaOperator getOperator() {
		return operator;
	}

	public void setOperator(CriteriaOperator operator) {
		this.operator = operator;
	}

	public QueryOperator getQueryOperator() {
		return queryOperator;
	}

	public void setQueryOperator(QueryOperator queryOperator) {
		this.queryOperator = queryOperator;
	}
}
