using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.SimpleExpressionElement;

namespace Iris.ExpressionElement
{
    public class ElseIfExpression : IBlockExpression
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

        public List<string> declaredVariables()
        {
            return null;
        }
    }
}
