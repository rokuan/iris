using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ExpressionElement
{
    public class DefaultExpression : SwitchCaseExpression, IBlockExpression
    {
        public Expression body;

        public DefaultExpression()
        {

        }

        public List<string> declaredVariables()
        {
            return null;
        }
    }
}
