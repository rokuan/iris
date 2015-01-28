package com.rokuan.iris.exception;

public class CaseException extends BaseException
{
	public CaseException(int lNum, int cNum)
	{
		super(lNum, cNum);   
	}

	public String ExceptionToString()
	{
		StringBuilder str = new StringBuilder();

		str.append(super.stringTrace());
		str.append("case Expression appears after default Expression");

		return str.toString();
	}
}
