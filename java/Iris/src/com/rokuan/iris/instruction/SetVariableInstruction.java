package com.rokuan.iris.instruction;

import com.rokuan.iris.expression.CharacterVariableExpression;
import com.rokuan.iris.expression.SimpleExpression;

public class SetVariableInstruction extends InstructionExpression
{
	public CharacterVariableExpression variable;
	public SimpleExpression value;

	public SetVariableInstruction()
	{

	}
}
