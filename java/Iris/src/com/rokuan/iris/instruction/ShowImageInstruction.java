package com.rokuan.iris.instruction;

import com.rokuan.iris.enums.Constants;
import com.rokuan.iris.expression.PathExpression;

public class ShowImageInstruction extends InstructionExpression
{
	public PathExpression image;
	//public Expression position;
	public Constants.Alignment position;

	public ShowImageInstruction()
	{

	}
}
