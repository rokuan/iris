package com.rokuan.iris.exception;

public class InvalidSequence extends BaseException
{
	public InvalidSequence(int lineNum, int colNum)
	{
		super(lineNum, colNum);
	}

	public String ExceptionToString()
	{
		StringBuilder str = new StringBuilder();

		str.append("invalid sequence: ");
		str.append(super.stringTrace());

		return str.toString();
	}
}
