package com.rokuan.iris.instruction;

import com.rokuan.iris.expression.SimpleExpression;

public class AssignExpression extends InstructionExpression
{
	//public PathExpression path;
	public String name;
	public SimpleExpression value;

	public AssignExpression()
	{

	}

	public AssignExpression(String varName, SimpleExpression varValue)
	{

	}
}
