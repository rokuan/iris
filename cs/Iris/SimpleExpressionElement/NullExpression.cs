using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.SimpleExpressionElement
{
    public class NullExpression : SimpleExpression
    {
        public NullExpression()
        {

        }

        /*public override string ToString()
        {
            return "null";
        }

        public override string ToString(string alinea)
        {
            return this.ToString();
        }*/

        public override ObjectType getExpressionType()
        {
            return ObjectType.undefinedType;
        }

    }
}
