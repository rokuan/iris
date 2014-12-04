using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.SimpleExpressionElement;

namespace Iris.ExpressionElement
{
    public class WhileExpression : Expression
    {
        public SimpleExpression condition;

        public Expression body;

        public WhileExpression()
        {

        }
    }
}
