package com.rokuan.iris.expression;

import java.util.ArrayList;

public class DefaultExpression extends SwitchCaseExpression implements IBlockExpression
{
	public Expression body;

	public DefaultExpression()
	{

	}

	public ArrayList<String> declaredVariables()
	{
		return null;
	}

	/*public override string ToString(string alinea)
        {
            StringBuilder str = new StringBuilder();

            str.Append(alinea);
            str.Append("default:\n");

            if (body != null)
            {
                str.Append(body.ToString(alinea + '\t'));
                str.Append('\n');
            }

            str.Append(alinea + '\t');
            str.Append("break;");

            return str.ToString();
        }*/
}
