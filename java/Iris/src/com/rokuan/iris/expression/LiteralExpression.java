package com.rokuan.iris.expression;

import com.rokuan.iris.parser.Token;
import com.rokuan.iris.types.ObjectType;

public class LiteralExpression extends SimpleExpression
{
	public Token value;

	public LiteralExpression(Token val)
	{
		this.value = val;
	}

	public ObjectType getExpressionType()
	{
		switch (value.type)
		{
		case STRING:
			return ObjectType.stringType;

		case CHAR:
			return ObjectType.charType;

		case INT:
			return ObjectType.intType;

		case BOOLEAN:
			return ObjectType.booleanType;

		default:		
			return ObjectType.undefinedType; 
		}
	}

	public String toString()
	{
		switch (value.type)
		{
		case BOOLEAN:
			return "" + value.booleanValue;

		case INT:
			return "" + value.intValue;

		case CHAR:
			return "'" + value.charValue + "'";

		case STRING:
			return '"' + value.stringValue + '"';

		default:
			return "";
		}
	}

	/*public override string ToString(string alinea)
        {
            return this.ToString();
        }*/
}
