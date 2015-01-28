package com.rokuan.iris.exception;

import com.rokuan.iris.parser.Token;

public class SyntaxError extends BaseException
{
	public Token.TokenValue tok;

	public SyntaxError(int lineNumber, int colNumber, Token.TokenValue expectedTok)
	{
		super(lineNumber, colNumber);
		tok = expectedTok;
	}

	public String ExceptionToString()
	{
		StringBuilder str = new StringBuilder();

		str.append("syntax error: ");
		str.append(super.stringTrace());
		str.append("expected ");
		str.append(Token.tokenstring(tok));

		return str.toString();
	}
}
