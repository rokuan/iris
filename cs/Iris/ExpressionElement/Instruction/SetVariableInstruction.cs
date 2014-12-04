using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.SimpleExpressionElement;

namespace Iris.ExpressionElement.Instruction
{
    public class SetVariableInstruction : InstructionExpression
    {
        public CharacterVariableExpression variable;
        public SimpleExpression value;

        public SetVariableInstruction()
        {

        }
    }
}
