package com.rokuan.iris.expression;

import java.util.ArrayList;

public class IfExpression extends Expression implements IBlockExpression
{
	public SimpleExpression condition;
	public Expression corps;

	public ArrayList<ElseIfExpression> elseIfList;

	public ElseExpression elseBlock;

	public IfExpression()
	{

	}

	public IfExpression(SimpleExpression conditionIf, Expression corpsIf)
	{
		this.condition = conditionIf;
		this.corps = corpsIf;
	}

	public void addElseIf(ElseIfExpression elseIfExp)
	{
		if (elseIfList == null)
		{
			elseIfList = new ArrayList<ElseIfExpression>();
		}

		elseIfList.add(elseIfExp);
	}

	public void addElse(ElseExpression elseExp)
	{
		elseBlock = elseExp;
	}

	public boolean hasElseBlock()
	{
		return (elseBlock != null);
	}

	public ArrayList<String> declaredVariables()
	{
		return null;
	}

	/*public override string ToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append("if(");
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
            str.Append("if(");
            str.Append(condition.ToString());
            str.Append("){");

            if (corps != null)
            {
                str.Append('\n');
                str.Append(corps.ToString(alinea + '\t'));
                str.Append('\n');
                str.Append(alinea);
                str.Append("}\n");
            }
            else
            {
                str.Append("}\n");
            }

            if (elseIfList != null)
            {
                foreach (ElseIfExpression elseIf in elseIfList)
                {
                    str.Append(elseIf.ToString(alinea));
                    str.Append('\n');
                }
            }

            if (elseBlock != null)
            {
                str.Append(elseBlock.ToString(alinea));
                str.Append('\n');
            }

            return str.ToString();

        }*/
}
