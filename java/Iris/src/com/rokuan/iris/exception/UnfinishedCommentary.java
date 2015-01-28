package com.rokuan.iris.exception;

public class UnfinishedCommentary extends BaseException
{
	public UnfinishedCommentary(int lineNum, int colNum)
	{
		super(lineNum, colNum);
	}

	public String ExceptionToString()
	{
		StringBuilder str = new StringBuilder();

		str.append("unfinished commentary: started at ");
		str.append(super.stringTrace());

		return str.toString();
	}
}
