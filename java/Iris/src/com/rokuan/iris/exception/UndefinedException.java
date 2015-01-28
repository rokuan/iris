package com.rokuan.iris.exception;

public class UndefinedException extends BaseException
{
	public UndefinedException(int lNum, int cNum)
	{
		super(lNum, cNum);
	}

	public String ExceptionToString()
	{
		return super.stringTrace();
	}
}
