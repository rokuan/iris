package com.rokuan.iris.expression;

import com.rokuan.iris.types.ObjectType;

public class ConstantExpression extends Expression
{
	public ConstantExpression()
	{

	}

	public ObjectType getExpressionType()
	{
		return ObjectType.intType;
	}
}
