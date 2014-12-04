using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.SimpleExpressionElement;

namespace Iris.ExpressionElement.Instruction
{
    public class SetBackgroundInstruction : InstructionExpression
    {
        //public PathExpression image;
        public VariableExpression image;

        public SetBackgroundInstruction()
        {

        }

        //public SetBackgroundInstruction(PathExpression exp)
        public SetBackgroundInstruction(VariableExpression exp)
        {
            image = exp;
        }
    }
}
