package com.rokuan.iris.expression;

import java.util.ArrayList;

public class ElseIfExpression implements IBlockExpression
{
	public SimpleExpression condition;
	public Expression corps;

	public ElseIfExpression()
	{

	}

	public ElseIfExpression(SimpleExpression conditionIf, Expression corpsIf)
	{
		this.condition = conditionIf;
		this.corps = corpsIf;
	}

	public ArrayList<String> declaredVariables()
	{
		return null;
	}

	/*public override string ToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append("else if(");
            str.Append(condition.ToString());
            str.Append("){");

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
            str.Append("else if(");
            str.Append(condition.ToString());
            str.Append("){");

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
