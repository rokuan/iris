package com.rokuan.iris.expression;

import com.rokuan.iris.types.ObjectType;

public class SubSimpleExpression extends SimpleExpression
{
	public SimpleExpression exp;

	public SubSimpleExpression(SimpleExpression e)
	{
		exp = e;
	}

	public String ToString()
	{
		return '(' + exp.toString() + ')';
	}

	public ObjectType getExpressionType()
	{
		return exp.getExpressionType();
	}
}
