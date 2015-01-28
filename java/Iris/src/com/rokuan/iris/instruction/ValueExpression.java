package com.rokuan.iris.instruction;

import com.rokuan.iris.expression.SimpleExpression;
import com.rokuan.iris.types.ObjectType;
//Int x = 3;

public class ValueExpression extends InstructionExpression
{
	public ObjectType type;
	public String name;
	public SimpleExpression value;

	public ValueExpression()
	{

	}

	public ValueExpression(ObjectType varType, String varName, SimpleExpression varValue)
	{
		this.type = varType;
		this.name = varName;
		this.value = varValue;
	}
}
