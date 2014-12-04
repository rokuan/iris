using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.SimpleExpressionElement;

namespace Iris.ExpressionElement.Instruction
{
    //msg "Hello World !";

    public class MsgDisplayInstruction : InstructionExpression
    {
        public SimpleExpression msg;

        public MsgDisplayInstruction()
        {

        }
    }
}
