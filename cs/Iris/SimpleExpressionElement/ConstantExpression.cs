using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.SimpleExpressionElement
{
    public class ConstantExpression : SimpleExpression
    {
        public ConstantExpression()
        {

        }

        public override ObjectType getExpressionType()
        {
            return ObjectType.intType;
        }
    }
}
