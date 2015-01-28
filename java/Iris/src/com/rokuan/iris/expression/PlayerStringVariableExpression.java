package com.rokuan.iris.expression;

import com.rokuan.iris.types.ObjectType;

public class PlayerStringVariableExpression extends CharacterVariableExpression {
	public PlayerStringVariableExpression(String varName) {
		super(varName);
	}

	public ObjectType getExpressionType() {
		return ObjectType.stringType;
	}
}
