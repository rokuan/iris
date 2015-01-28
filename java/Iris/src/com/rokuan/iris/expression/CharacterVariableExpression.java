package com.rokuan.iris.expression;

public abstract class CharacterVariableExpression extends SimpleExpression {
	public String name;

	public CharacterVariableExpression() {

	}

	public CharacterVariableExpression(String varName) {
		name = varName;
	}
}
