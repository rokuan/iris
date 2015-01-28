package com.rokuan.iris.exception;

import com.rokuan.iris.parser.Token;

public class InvalidRightTypeOperand extends InvalidTypeOperand
{
	public InvalidRightTypeOperand(int lNum, int cNum, Token.TokenValue token)
	{
		super(lNum, cNum, token);            
	}

	public String ExceptionToString()
	{
		return super.ExceptionToString("right");
	}
}
