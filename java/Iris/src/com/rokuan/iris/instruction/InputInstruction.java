package com.rokuan.iris.instruction;

public class InputInstruction extends InstructionExpression
{
	public String name;

	public InputInstruction()
	{

	}

	public InputInstruction(String varName)
	{
		name = varName;
	}
}
