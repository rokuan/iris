using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.SimpleExpressionElement
{
    public abstract class SimpleExpression : ExpressionElement.Expression
    {
        public abstract ObjectType getExpressionType();
    }
}
