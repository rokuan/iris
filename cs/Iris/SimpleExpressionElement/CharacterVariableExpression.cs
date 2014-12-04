using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.SimpleExpressionElement
{
    public class CharacterVariableExpression : SimpleExpression
    {
        public string name;

        public CharacterVariableExpression()
        {

        }

        public CharacterVariableExpression(string varName)
        {
            name = varName;
        }

        /*public override ObjectType getExpressionType()
        {
            return ObjectType.undefinedType;
        }*/

        public override ObjectType getExpressionType()
        {
            return ObjectType.intType;
        }
    }
}
