package com.rokuan.iris.expression;

import com.rokuan.iris.parser.Token;
import com.rokuan.iris.types.ObjectType;

public class UnaryOperatorExpression extends SimpleExpression
{
	public Token.TokenValue op;
	public SimpleExpression exp;

	public UnaryOperatorExpression(Token.TokenValue operatorToken, SimpleExpression expr)
	{
		op = operatorToken;
		exp = expr;
	}

	public ObjectType getExpressionType()
	{
		if (op == Token.TokenValue.NEG)
		{
			return ObjectType.booleanType;
		}

		return ObjectType.undefinedType;
	}
}
