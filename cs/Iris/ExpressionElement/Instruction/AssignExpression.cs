using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.SimpleExpressionElement;

namespace Iris.ExpressionElement.Instruction
{
    public class AssignExpression : InstructionExpression
    {
        //public PathExpression path;
        public string name;
        public SimpleExpression value;

        public AssignExpression()
        {

        }

        public AssignExpression(string varName, SimpleExpression varValue)
        {

        }
    }
}
