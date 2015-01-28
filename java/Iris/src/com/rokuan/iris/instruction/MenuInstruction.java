package com.rokuan.iris.instruction;

import java.util.ArrayList;

import com.rokuan.iris.expression.CaseExpression;
import com.rokuan.iris.expression.DefaultExpression;
import com.rokuan.iris.expression.SwitchCaseExpression;

public class MenuInstruction extends InstructionExpression
{
	//menu("Oui je veux bien", "Non merci, ca ira", "Avec plaisir"){
	//  case 0:
	//  case 2:
	//      open;
	//      msg "Tres bien suivez moi";
	//      close;
	//      break;
	//
	//  case 1:
	//      open;
	//      msg "Oh mais pourquoi etes vous si serieux ?";
	//      close;
	//}

	public ArrayList<String> choices;
	public ArrayList<SwitchCaseExpression> cases;

	public boolean hasDefaultCase = false;

	public ArrayList<Integer> intCases;

	public MenuInstruction()
	{

	}

	public void addChoice(String choice)
	{
		if (choices == null)
		{
			choices = new ArrayList<String>();
		}

		choices.add(choice);
	}

	public ArrayList<String> declaredVariables()
	{
		return null;
	}

	public void addCase(SwitchCaseExpression ce)
	{
		if (cases == null)
		{
			cases = new ArrayList<SwitchCaseExpression>();
		}

		cases.add(ce);

		if (ce instanceof DefaultExpression)
		{
			hasDefaultCase = true;
			return;
		}

		if (ce instanceof CaseExpression)
		{
			SwitchCaseExpression tmp = ce;

			while (tmp != null)
			{
				if (tmp instanceof DefaultExpression)
				{
					hasDefaultCase = true;
					return;
				}
				else
				{
					CaseExpression caseExp = (CaseExpression)tmp;

					if (intCases == null)
					{
						intCases = new ArrayList<Integer>();
					}

					intCases.add(caseExp.literal.value.intValue);
					tmp = caseExp.next;
				}
			}
		}
	}
}
