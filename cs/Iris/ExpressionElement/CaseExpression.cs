using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.SimpleExpressionElement;

namespace Iris.ExpressionElement
{
    class CaseExpression : SwitchCaseExpression, IBlockExpression
    {
        public LiteralExpression literal;
        public Expression body;
        public SwitchCaseExpression next;

        public CaseExpression()
        {

        }

        public CaseExpression(LiteralExpression literalValue, Expression bodyExp)
        {
            this.literal = literalValue;
        }

        public List<string> declaredVariables()
        {
            return null;
        }
    }
}
