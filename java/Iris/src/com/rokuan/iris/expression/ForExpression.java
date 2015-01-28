package com.rokuan.iris.expression;

import java.util.ArrayList;

import com.rokuan.iris.instruction.AssignExpression;

public class ForExpression extends Expression implements IBlockExpression
{
	public AssignExpression init;
	public SimpleExpression condition;
	public AssignExpression maj;

	public Expression body;

	public ForExpression()
	{

	}

	public ArrayList<String> declaredVariables()
	{
		return null;
	}
}
