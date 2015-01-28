package com.rokuan.iris.expression;

import com.rokuan.iris.parser.Token;
import com.rokuan.iris.types.ObjectType;

public class BinaryOperatorExpression extends SimpleExpression
{
	public Token.TokenValue op;
	public SimpleExpression e1;
	public SimpleExpression e2;

	public BinaryOperatorExpression(Token.TokenValue opValue, SimpleExpression left, SimpleExpression right)
	{
		this.op = opValue;
		this.e1 = left;
		this.e2 = right;
	}

	public ObjectType getExpressionType()
	{
		ObjectType e1Type = e1.getExpressionType(), e2Type = e2.getExpressionType();

		switch (op)
		{
		case EQBOOL:
			if (e1Type.equals(e2Type))
			{
				return ObjectType.booleanType;
			}
			break;

		case AND:
		case OR:
			if (e1Type.equals(ObjectType.booleanType)
			&& e2Type.equals(ObjectType.booleanType))
			{
				return ObjectType.booleanType;
			}
			break;

		case LT:
		case GT:
		case LEQ:
		case GEQ:
			if (e1Type.equals(ObjectType.intType)
			&& e2Type.equals(ObjectType.intType))
			{
				return ObjectType.booleanType;
			}
			break;

		case PLUS:
			if (e1Type.equals(ObjectType.stringType)
			|| e2Type.equals(ObjectType.stringType))
			{
				return ObjectType.stringType;
			}
			else if (e1Type.equals(ObjectType.intType)
					&& e2Type.equals(ObjectType.intType))
			{
				return ObjectType.intType;
			}

			//erreur
			break;
			//return ObjectType.intType;

		case MINUS:
		case TIMES:
		case DIV:
			if (e1Type.equals(ObjectType.intType)
			&& e2Type.equals(ObjectType.intType))
			{
				return ObjectType.intType;
			}

			//erreur
			break;
			//return ObjectType.intType;

		default:
			return ObjectType.undefinedType;

		}

		return ObjectType.undefinedType;

	}

	public String ToString()
	{
		StringBuilder str = new StringBuilder();

		str.append('(');
		str.append(e1.toString());
		str.append(' ');

		switch (op)
		{
		case AND:
			str.append("&&");
			break;
		case OR:
			str.append("||");
			break;

		case EQBOOL:
			str.append("==");
			break;
		case LT:
			str.append('<');
			break;
		case GT:
			str.append('>');
			break;
		case LEQ:
			str.append("<=");
			break;
		case GEQ:
			str.append(">=");
			break;

		case PLUS:
			str.append('+');
			break;
		case MINUS:
			str.append('-');
			break;
		case TIMES:
			str.append('*');
			break;
		case DIV:
			str.append('/');
			break;

		default:
			return "";
		}

		str.append(' ');
		str.append(e2.toString());
		str.append(')');

		return str.toString();
	}
}
