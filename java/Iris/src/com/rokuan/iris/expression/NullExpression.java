package com.rokuan.iris.expression;

import com.rokuan.iris.types.ObjectType;

public class NullExpression extends SimpleExpression
{
	public NullExpression()
	{

	}

	/*public override string ToString()
        {
            return "null";
        }

        public override string ToString(string alinea)
        {
            return this.ToString();
        }*/

	public ObjectType getExpressionType()
	{
		return ObjectType.undefinedType;
	}
}
