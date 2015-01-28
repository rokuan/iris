package com.rokuan.iris.instruction;

import com.rokuan.iris.expression.Expression;
import com.rokuan.iris.expression.PathExpression;
import com.rokuan.iris.parser.Token;

// x++

public class VariableUpdate extends Expression implements IInstruction
{
	public PathExpression variable;
	public Token.TokenValue op;

	public VariableUpdate(PathExpression varPath, Token.TokenValue opUpdate)
	{
		this.variable = varPath;
		this.op = opUpdate;
	}
}
