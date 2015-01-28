package com.rokuan.iris.exception;

public class DefaultCaseException extends BaseException
{
	public DefaultCaseException(int lNum, int cNum)
	{
		super(lNum, cNum);
	}

	public String ExceptionToString()
	{
		StringBuilder str = new StringBuilder();

		str.append(super.stringTrace());
		str.append("default case already exists");

		return str.toString();
	}
}
