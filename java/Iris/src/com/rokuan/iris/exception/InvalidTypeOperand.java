package com.rokuan.iris.exception;

import com.rokuan.iris.parser.Token;

public abstract class InvalidTypeOperand extends BaseException
{
	public Token.TokenValue tok;

	public InvalidTypeOperand(int lNum, int cNum, Token.TokenValue token)
	{
		super(lNum, cNum);
		tok = token;
	}

	public String ExceptionToString(String side)
	{
		StringBuilder str = new StringBuilder();

		str.append("invalid ");
		str.append(side);
		str.append(" operand for ");
				str.append(Token.tokenstring(tok));

				return str.toString();
	}
}
