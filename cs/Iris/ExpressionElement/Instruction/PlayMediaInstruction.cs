using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.SimpleExpressionElement;
using Iris.ParserUtils;

namespace Iris.ExpressionElement.Instruction
{
    public class PlayMediaInstruction : InstructionExpression
    {
        //public PathExpression path;
        public string name;
        public LiteralExpression loop = new LiteralExpression(new Token(Token.TokenValue.BOOLEAN, false));

        public PlayMediaInstruction()
        {

        }
    }
}
