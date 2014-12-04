using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.SimpleExpressionElement;
using Iris.ExpressionElement.Instruction;

namespace Iris.ExpressionElement
{
    public class ForExpression : Expression, IBlockExpression
    {
        public AssignExpression init;
        public SimpleExpression condition;
        public AssignExpression maj;

        public Expression body;

        public ForExpression()
        {

        }

        public List<string> declaredVariables()
        {
            return null;
        }
    }
}
