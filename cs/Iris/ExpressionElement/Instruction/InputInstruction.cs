using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ExpressionElement.Instruction
{
    public class InputInstruction : InstructionExpression
    {
        public string name;

        public InputInstruction()
        {

        }

        public InputInstruction(string varName)
        {
            name = varName;
        }
    }
}
