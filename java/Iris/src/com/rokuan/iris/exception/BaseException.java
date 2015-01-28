package com.rokuan.iris.exception;

public abstract class BaseException extends Exception
{
	protected int lineNum;
	protected int columnNum;

	public BaseException(int lineNumber, int columnNumber)
	{
		this.lineNum = lineNumber;
		this.columnNum = columnNumber;
	}

	public String stringTrace()
	{
		StringBuilder str = new StringBuilder();

		str.append("line ");
		str.append(lineNum);
		str.append(", char ");
		str.append(columnNum);
		str.append(' ');

		return str.toString();
	}

	public abstract String ExceptionToString();

	public String toString(){
		return this.ExceptionToString();
	}
}
