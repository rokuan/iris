package com.rokuan.iris.expression;

import java.util.ArrayList;

import com.rokuan.iris.parser.Token;

public class SwitchExpression extends Expression {
	public Token value;
	public SimpleExpression exp;

	public boolean hasDefaultCase = false;

	public ArrayList<SwitchCaseExpression> cases;

	public ArrayList<String> stringCases;
	public ArrayList<Integer> intCases;

	public SwitchExpression() {

	}

	/*
	 * public override ObjectType getExpressionType() { return
	 * exp.getExpressionType(); }
	 */

	public ArrayList<String> declaredVariables() {
		return null;
	}

	/*
	 * public override string ToString(string alinea) {
	 * 
	 * StringBuilder str = new StringBuilder();
	 * 
	 * str.Append(alinea); str.Append("switch("); str.Append(exp);
	 * str.Append("){\n");
	 * 
	 * if (cases != null) { foreach (SwitchCaseExpression caseExp in cases) {
	 * str.Append(caseExp.ToString(alinea)); str.Append('\n'); } }
	 * 
	 * str.Append("}");
	 * 
	 * return str.ToString();
	 * 
	 * }
	 */

	public void addCase(SwitchCaseExpression ce) {
		if (cases == null) {
			cases = new ArrayList<SwitchCaseExpression>();
		}

		cases.add(ce);

		if (ce instanceof DefaultExpression) {
			hasDefaultCase = true;
			return;
		}

		if (ce instanceof CaseExpression) {

			SwitchCaseExpression tmp = ce;

			while (tmp != null) {
				if (tmp instanceof DefaultExpression) {
					hasDefaultCase = true;
					return;
				} else {
					CaseExpression caseExp = (CaseExpression) tmp;

					switch (caseExp.literal.value.type) {
					case STRING:
						if (stringCases == null) {
							stringCases = new ArrayList<String>();
						}

						stringCases.add(caseExp.literal.value.stringValue);
						break;

					case INT:
						if (intCases == null) {
							intCases = new ArrayList<Integer>();
						}

						intCases.add(caseExp.literal.value.intValue);
						break;
					}

					tmp = caseExp.next;
				}
			}
		}

	}

	public boolean containsLiteral(SwitchCaseExpression sce) {
		if (sce instanceof DefaultExpression) {
			return false;
		}

		CaseExpression caseExp = (CaseExpression) sce;

		switch (caseExp.literal.value.type) {
		case STRING:
			if (stringCases == null) {
				return false;
			}

			return stringCases.contains(caseExp.literal.value.stringValue);

		case INT:
			if (intCases == null) {
				return false;
			}

			return intCases.contains(caseExp.literal.value.intValue);

		default:
			return false;
		}
	}

	public void addDefault(DefaultExpression de) {
		if (cases == null) {
			cases = new ArrayList<SwitchCaseExpression>();
		}

		cases.add(de);
		hasDefaultCase = true;
	}
}
