package com.rokuan.iris.expression;

import java.util.ArrayList;

public class DoWhileExpression extends Expression implements IBlockExpression
{
	public SimpleExpression condition;

	public Expression body;

	public DoWhileExpression()
	{

	}

	public ArrayList<String> declaredVariables()
	{
		return null;
	}
}
