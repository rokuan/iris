using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.SimpleExpressionElement;

namespace Iris.ExpressionElement
{
    public class DoWhileExpression : Expression, IBlockExpression
    {
        public SimpleExpression condition;

        public Expression body;

        public DoWhileExpression()
        {

        }

        public List<string> declaredVariables()
        {
            return null;
        }
    }
}
