using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.SimpleExpressionElement;

namespace Iris.ExpressionElement.Instruction
{
    //Int x = 3;

    public class ValueExpression : InstructionExpression
    {
        public ObjectType type;

        public string name;

        public SimpleExpression value;

        public ValueExpression()
        {

        }

        public ValueExpression(ObjectType varType, string varName, SimpleExpression varValue)
        {
            this.type = varType;
            this.name = varName;
            this.value = varValue;
        }
    }
}
