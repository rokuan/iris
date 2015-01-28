package com.rokuan.iris.expression;

import java.util.ArrayList;
import com.rokuan.iris.types.ObjectType;

public class NewInstanceExpression extends Expression
{
	public ObjectType type;
	public ArrayList<Expression> args;

	public NewInstanceExpression()
	{

	}

	public void addArgument(Expression arg)
	{
		if (args == null)
		{
			args = new ArrayList<Expression>();
		}

		args.add(arg);
	}
}
