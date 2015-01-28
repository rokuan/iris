package com.rokuan.iris.expression;

import java.util.ArrayList;

public class LabelExpression extends Expression implements IBlockExpression
{
	public String labelName;
	public Expression body;

	public LabelExpression(String lName)
	{
		this.labelName = lName;
	}

	public LabelExpression(String lName, Expression lBody)
	{
		this.labelName = lName;
		this.body = lBody;
	}

	public ArrayList<String> declaredVariables()
	{
		return null;
	}

	/*public override string ToString(string alinea)
        {
            StringBuilder str = new StringBuilder();

            str.Append(alinea);
            str.Append(labelName);
            str.Append(":\n");

            if (body != null)
            {
                str.Append(body.ToString(alinea + '\t'));
                str.Append('\n');
            }

            return str.ToString();
        }*/
}
