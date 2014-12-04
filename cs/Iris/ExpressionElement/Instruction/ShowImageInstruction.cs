using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.SimpleExpressionElement;

namespace Iris.ExpressionElement.Instruction
{
    public class ShowImageInstruction : InstructionExpression
    {
        public PathExpression image;
        //public SimpleExpression position;
        public int position;

        public ShowImageInstruction()
        {

        }
    }
}
