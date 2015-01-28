package com.rokuan.iris.expression;

import java.util.ArrayList;

public class ElseExpression implements IBlockExpression
{
	public Expression corps;

	public ElseExpression()
	{

	}

	public ElseExpression(Expression corpsElse)
	{
		this.corps = corpsElse;
	}

	public ArrayList<String> declaredVariables()
	{
		return null;
	}

	/*public override string ToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append("else{");

            if (corps != null)
            {
                str.Append("\n");
                str.Append(corps.ToString());
                str.Append("\n");
            }

            str.Append("}");

            return str.ToString();
        }

        public override string ToString(string alinea)
        {
            StringBuilder str = new StringBuilder();

            str.Append(alinea);
            str.Append("else{");

            if (corps != null)
            {
                str.Append('\n');
                str.Append(corps.ToString(alinea + '\t'));
                str.Append('\n');
                str.Append(alinea);
                str.Append('}');
            }
            else
            {
                str.Append("}");
            }

            return str.ToString();
        }*/
}
