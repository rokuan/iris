using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ExpressionElement
{
    interface IBlockExpression
    {
        List<string> declaredVariables();
    }
}
