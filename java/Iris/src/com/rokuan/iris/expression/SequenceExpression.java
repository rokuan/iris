package com.rokuan.iris.expression;

import java.util.ArrayList;

import com.rokuan.iris.instruction.ValueExpression;

public class SequenceExpression extends Expression
{
	public ArrayList<Expression> exps;
	public ArrayList<String> declaredVars = null;

	public SequenceExpression()
	{

	}

	public SequenceExpression(ArrayList<Expression> expList)
	{
		exps = expList;
		//exps = new List<Expression>();
		//exps.AddRange(expList);

		/*foreach (Expression e in expList)
            {
                exps.Add(e);
            }*/
		//updateDeclaredVariables();
	}

	/*public void updateDeclaredVariables()
        {
            if (exps == null)
            {
                return;
            }

            foreach (Expression exp in exps)
            {
                if (exp is ValueExpression)
                {
                    if (declaredVars == null)
                    {
                        declaredVars = new List<string>();
                    }

                    declaredVars.Add(((ValueExpression)exp).name);
                }
            }
        }*/

	public ArrayList<String> declaredVariables()
	{
		//return declaredVars;
		if (exps == null)
		{
			return null;
		}

		for (Expression exp: exps)
		{
			if (exp instanceof ValueExpression)
			{
				if (declaredVars == null)
				{
					declaredVars = new ArrayList<String>();
				}

				declaredVars.add(((ValueExpression)exp).name);
			}
		}

		return declaredVars;
	}
}
