package com.rokuan.iris.instruction;

import com.rokuan.iris.expression.LiteralExpression;
import com.rokuan.iris.parser.Token;

public class PlayMediaInstruction extends InstructionExpression
{
	//public PathExpression path;
	public String name;
	public LiteralExpression loop = new LiteralExpression(new Token(Token.TokenValue.BOOLEAN, false));

	public PlayMediaInstruction()
	{

	}
}
