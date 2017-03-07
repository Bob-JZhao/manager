package com.imm.common.jdbc.model;

// / <summary>
// / 标准运算符
// / </summary>
public enum CriteriaOperator {
	// / <summary>
	// / 等于
	// / </summary>
	Equal,
	// / <summary>
	// / 不等于
	// / </summary>
	NotEqual,
	// / <summary>
	// / 大于
	// / </summary>
	GreaterThan,
	// / <summary>
	// / 小于
	// / </summary>
	LesserThan,
	// / <summary>
	// / 大于等于
	// / </summary>
	GreaterThanOrEqual,
	// / <summary>
	// / 小于等于
	// / </summary>
	LesserThanOrEqual,
	// / <summary>
	// / like
	// / </summary>
	Like,
	// / <summary>
	// / not like
	// / </summary>
	NotLike,
	// / <summary>
	// / is null
	// / </summary>
	IsNull,
	// / <summary>
	// / is not null
	// / </summary>
	IsNotNull,
	// / <summary>
	// / in
	// / </summary>
	In,
	// / <summary>
	// / x&y
	// / </summary>
	Me, 
	// / <summary>
	// / x&y = 0
	// / </summary>
	NotMe
}
