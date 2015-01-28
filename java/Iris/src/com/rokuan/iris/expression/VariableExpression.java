package com.rokuan.iris.expression;

import com.rokuan.iris.types.ObjectType;

public class VariableExpression extends PathExpression
{
	public ObjectType variableType = null;

	public String name;

	public VariableExpression()
	{

	}

	public VariableExpression(String varName)
	{
		name = varName;
	}

	public ObjectType getExpressionType()
	{
		return ObjectType.undefinedType;
	}
}
