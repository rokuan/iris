package com.rokuan.iris.expression;

import com.rokuan.iris.instruction.InstructionExpression;

public class MethodCallExpression extends InstructionExpression
{
	/*public ObjectType methodType = null;

	public List<Expression> args
	{
		get;
		private set;
	}

	public string name
	{
		get;
		private set;
	}

	public MethodCallExpression(string funcName)
	{
		name = funcName;
	}

	public void addArgument(Expression se)
	{
		if (args == null)
		{
			args = new List<Expression>();
		}

		args.Add(se);
	}

	public override string ToString(string alinea)
	{
		StringBuilder str = new StringBuilder();

		str.Append(alinea);
		str.Append(name);

		str.Append('(');

		if (args != null)
		{
			str.Append(args.ElementAt(0));

			for (int i = 1; i < args.Count; i++)
			{
				str.Append(',');
				str.Append(args.ElementAt(i).ToString());
			}
		}

		str.Append(')');

		return str.ToString();
	}

	public override ObjectType getExpressionType()
	{
		if (methodType != null)
		{
			return methodType;
		}

		return ObjectType.undefinedType;
	}*/
}
