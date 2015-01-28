package com.rokuan.iris.exception;

public class UnfinishedString extends BaseException
{
	public UnfinishedString(int lineNum, int colNum)
	{
		super(lineNum, colNum);
	}

	public String ExceptionToString()
	{
		StringBuilder str = new StringBuilder();

		str.append("unfinished string: started at ");
		str.append(super.stringTrace());

		return str.toString();
	}
}
