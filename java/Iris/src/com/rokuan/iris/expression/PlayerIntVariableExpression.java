package com.rokuan.iris.expression;

import com.rokuan.iris.types.ObjectType;

public class PlayerIntVariableExpression extends CharacterVariableExpression {
	public PlayerIntVariableExpression(String varName) {
		super(varName);
	}

	public ObjectType getExpressionType() {
		return ObjectType.intType;
	}
}
