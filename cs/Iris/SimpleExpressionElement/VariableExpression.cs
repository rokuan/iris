using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.SimpleExpressionElement
{
    //myvar

    public class VariableExpression : PathExpression
    {
        public ObjectType variableType = null;

        public string name;

        public VariableExpression()
        {

        }

        public VariableExpression(string varName)
        {
            name = varName;
        }

        /*public override string ToString()
        {
            return name;
        }


        public override string ToString(string alinea)
        {
            return name;
        }*/

        public override ObjectType getExpressionType()
        {
            /*if (variableType != null)
            {
                return variableType;
            }*/

            return ObjectType.undefinedType;
            //return ObjectType.booleanType;
        }
    }
}
