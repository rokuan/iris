package com.rokuan.iris.exception;

public class NewLineInStringException extends BaseException
{
	public NewLineInStringException(int lNum, int cNum)
	{
		super(lNum, cNum);
	}

	public String ExceptionToString()
	{
		StringBuilder str = new StringBuilder();

		str.append("unexpected new line in string: ");
		str.append(super.stringTrace());

		return str.toString();
	}
}