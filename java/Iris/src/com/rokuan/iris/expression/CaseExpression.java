package com.rokuan.iris.expression;

import java.util.ArrayList;

public class CaseExpression extends SwitchCaseExpression implements IBlockExpression
{
	public LiteralExpression literal;
	public Expression body;
	public SwitchCaseExpression next;

	public CaseExpression()
	{

	}

	public CaseExpression(LiteralExpression literalValue, Expression bodyExp)
	{
		this.literal = literalValue;
	}

	public ArrayList<String> declaredVariables()
	{
		return null;
	}
}
