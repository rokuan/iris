package com.rokuan.iris.exception;

import com.rokuan.iris.parser.Token;

public class InvalidLeftTypeOperand extends InvalidTypeOperand
{
	public InvalidLeftTypeOperand(int lNum, int cNum, Token.TokenValue token)
	{
		super(lNum, cNum, token);
	}

	public String ExceptionToString()
	{
		return super.ExceptionToString("left");
	}
}
