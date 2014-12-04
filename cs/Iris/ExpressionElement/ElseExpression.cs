using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ExpressionElement
{
    public class ElseExpression : IBlockExpression
    {
        public Expression body;

        public ElseExpression()
        {

        }

        public ElseExpression(Expression elseBody)
        {
            this.body = elseBody;
        }

        public List<string> declaredVariables()
        {
            return null;
        }
    }
}
