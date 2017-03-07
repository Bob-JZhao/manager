package com.imm.common.jdbc.model;
/// <summary>
/// 排序子句
/// </summary>
public class OrderClause
{
    private String propertyName;
    private OrderClauseCriteria criterion;

    /// <summary>
    /// 初始化函数
    /// </summary>
    /// <param name="propertyName">要排序的属性</param>
    /// <param name="criteria">排序规则</param>
    public OrderClause(String propertyName, OrderClauseCriteria criteria)
    {
        this.propertyName = propertyName;
        this.criterion = criteria;
    }

    public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public OrderClauseCriteria getCriterion() {
		return criterion;
	}

	public void setCriterion(OrderClauseCriteria criterion) {
		this.criterion = criterion;
	}

	/// <summary>
    /// 排序
    /// </summary>
    public enum OrderClauseCriteria
    {
        Ascending,
        Descending
    }
}