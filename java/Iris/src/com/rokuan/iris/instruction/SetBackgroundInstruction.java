package com.rokuan.iris.instruction;

import com.rokuan.iris.expression.VariableExpression;

public class SetBackgroundInstruction extends InstructionExpression
{
	//public PathExpression image;
	public VariableExpression image;

	public SetBackgroundInstruction()
	{

	}

	//public SetBackgroundInstruction(PathExpression exp)
	public SetBackgroundInstruction(VariableExpression exp)
	{
		image = exp;
	}
}
