using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils.LexParseException
{
    public class InvalidLeftTypeOperand : InvalidTypeOperand
    {
        public InvalidLeftTypeOperand(int lNum, int cNum, Token.TokenValue token)
            : base(lNum, cNum, token)
        {
            
        }

        public override string ExceptionToString()
        {
            return base.ExceptionToString("left");
        }
    }
}
