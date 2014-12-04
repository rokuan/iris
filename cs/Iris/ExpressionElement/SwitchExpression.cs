using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.ParserUtils;
using Iris.SimpleExpressionElement;

namespace Iris.ExpressionElement
{
    public class SwitchExpression : Expression
    {
        public Token value
        {
            get;
            private set;
        }

        public SimpleExpression exp;

        public bool hasDefaultCase = false;

        public List<SwitchCaseExpression> cases
        {
            get;
            private set;
        }

        public List<string> stringCases;
        public List<int> intCases;

        public SwitchExpression()
        {

        }

        public List<string> declaredVariables()
        {
            return null;
        }

        public void addCase(SwitchCaseExpression ce)
        {
            if (cases == null)
            {
                cases = new List<SwitchCaseExpression>();
            }

            cases.Add(ce);

            if (ce is DefaultExpression)
            {
                hasDefaultCase = true;
                return;
            }

            if (ce is CaseExpression)
            {
                SwitchCaseExpression tmp = ce;

                while (tmp != null)
                {
                    if (tmp is DefaultExpression)
                    {
                        hasDefaultCase = true;
                        return;
                    }
                    else
                    {
                        CaseExpression caseExp = (CaseExpression)tmp;

                        switch (caseExp.literal.value.type)
                        {
                            case Token.TokenValue.STRING:
                                if (stringCases == null)
                                {
                                    stringCases = new List<string>();
                                }

                                stringCases.Add(caseExp.literal.value.stringValue);
                                break;

                            case Token.TokenValue.INT:
                                if (intCases == null)
                                {
                                    intCases = new List<int>();
                                }

                                intCases.Add(caseExp.literal.value.intValue);
                                break;
                        }

                        tmp = caseExp.next;
                    }
                }
            }
        }

        public bool containsLiteral(SwitchCaseExpression sce)
        {
            if (sce is DefaultExpression)
            {
                return false;
            }

            CaseExpression caseExp = (CaseExpression)sce;

            switch (caseExp.literal.value.type)
            {
                case Token.TokenValue.STRING:
                    if (stringCases == null)
                    {
                        return false;
                    }

                    return stringCases.Contains(caseExp.literal.value.stringValue);

                case Token.TokenValue.INT:
                    if (intCases == null)
                    {
                        return false;
                    }

                    return intCases.Contains(caseExp.literal.value.intValue);
            }

            return true;
        }

        public void addDefault(DefaultExpression de)
        {
            if (cases == null)
            {
                cases = new List<SwitchCaseExpression>();
            }

            cases.Add(de);
            hasDefaultCase = true;
        }
    }
}
