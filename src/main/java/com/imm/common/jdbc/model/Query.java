package com.imm.common.jdbc.model;

import java.util.ArrayList;
import java.util.List;

import com.imm.common.jdbc.model.OrderClause.OrderClauseCriteria;

public class Query {
	private List<Criterion> criteria = new ArrayList<Criterion>();
	private QueryOperator operator;
	private List<Query> subQueries = new ArrayList<Query>();
	private List<OrderClause> orderClauses = new ArrayList<OrderClause>();
	private List<Object> values = new ArrayList<Object>();

	public List<Criterion> getCriteria() {
		return criteria;
	}

	public List<Object> getValues() {
		return values;
	}

	public void setCriteria(List<Criterion> criteria) {
		this.criteria = criteria;
	}

	public QueryOperator getOperator() {
		return operator;
	}

	public void setOperator(QueryOperator operator) {
		this.operator = operator;
	}

	public List<Query> getSubQueries() {
		return subQueries;
	}

	public void setSubQueries(List<Query> subQueries) {
		this.subQueries = subQueries;
	}

	public List<OrderClause> getOrderClauses() {
		return orderClauses;
	}

	public void setOrderClauses(List<OrderClause> orderClauses) {
		this.orderClauses = orderClauses;
	}

	public String toQueryString() {
		StringBuffer sb = new StringBuffer();
		if (criteria != null && criteria.size() > 0) {
			sb.append(" where ");
		}
		int i = 0;
		for (Criterion item : criteria) {
			i++;
			if (i != 1) {
				sb.append(" " + item.getQueryOperator().toString() + " ");
			}
			sb.append(item.getPropertyName());
			if (item.getOperator().equals(CriteriaOperator.Equal)) {
				sb.append("=?");
			} else if (item.getOperator().equals(CriteriaOperator.NotEqual)) {
				sb.append("!=?");
			} else if (item.getOperator().equals(CriteriaOperator.In)) {
				sb.append(" in(" + item.getValue() + ")");
			} else if (item.getOperator().equals(CriteriaOperator.Like)) {
				sb.append(" like ?");
			} else if (item.getOperator().equals(CriteriaOperator.Me)) {
				sb.append("&?");
			} else if (item.getOperator().equals(CriteriaOperator.NotMe)) {
				sb.append("&?=0");
			}
			if (!item.getOperator().equals(CriteriaOperator.In)) {
				values.add(item.getValue());
			}
		}
		if (orderClauses != null && orderClauses.size() > 0) {
			sb.append(" order by ");
		}
		i = 0;
		for (OrderClause item : orderClauses) {
			i++;
			if (item.getCriterion().equals(OrderClauseCriteria.Ascending)) {
				sb.append(item.getPropertyName() + " asc");
			} else {
				sb.append(item.getPropertyName() + " desc");
			}
			if (i != orderClauses.size()) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
}
