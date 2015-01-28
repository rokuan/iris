package com.rokuan.iris.expression;

import java.util.ArrayList;

import com.rokuan.iris.instruction.IInstruction;
import com.rokuan.iris.types.ObjectType;

public class MacroCallExpression extends SimpleExpression implements
		IInstruction {

	public enum MacroValue {
		RAND, SLEEP, GETTIME, GETFULLTIME, GETDATE, GETFULLDATE, COUNTITEM, DELETEITEM, GETITEM
	}

	public String name;
	public MacroValue macro;
	public ArrayList<SimpleExpression> arguments = new ArrayList<SimpleExpression>();

	public void addArgument(SimpleExpression se) {
		arguments.add(se);
	}

	@Override
	public ObjectType getExpressionType() {
		switch (macro) {
		case GETDATE:
		case GETTIME:
		case COUNTITEM:
			return ObjectType.intType;
		case GETFULLDATE:
		case GETFULLTIME:
			return ObjectType.stringType;
		case GETITEM:
		case DELETEITEM:
			return ObjectType.booleanType;
			// case SLEEP:
		default:
			return ObjectType.undefinedType;
		}
	}
}
