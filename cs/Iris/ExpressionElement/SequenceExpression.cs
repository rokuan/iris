using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.ExpressionElement.Instruction;

namespace Iris.ExpressionElement
{
    public class SequenceExpression : Expression
    {
        public List<Expression> exps;
        public List<string> declaredVars = null;

        public SequenceExpression()
        {

        }

        public SequenceExpression(List<Expression> expList)
        {
            exps = expList;
        }

        public List<string> declaredVariables()
        {
            if (exps == null)
            {
                return null;
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

            return declaredVars;
        }
    }
}
