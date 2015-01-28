package com.rokuan.iris.exception;

public class EmptyExpression extends BaseException
{
	public EmptyExpression(int lNum, int cNum)
	{
		super(lNum, cNum);
	}

	public String ExceptionToString()
	{
		StringBuilder str = new StringBuilder();

		str.append("empty Expression: ");
		str.append(super.stringTrace());

		return str.toString();
	}
}
