using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.SimpleExpressionElement
{
    public class SubSimpleExpression : SimpleExpression
    {
        public SimpleExpression exp;

        public SubSimpleExpression(SimpleExpression e)
        {
            exp = e;
        }

        public override string ToString()
        {
            return '(' + exp.ToString() + ')';
        }

        public override ObjectType getExpressionType()
        {
            return exp.getExpressionType();
        }
    }
}
