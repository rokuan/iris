using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils.LexParseException
{
    public abstract class InvalidTypeOperand : BaseException
    {
        public Token.TokenValue tok;

        public InvalidTypeOperand(int lNum, int cNum, Token.TokenValue token)
            : base(lNum, cNum)
        {
            tok = token;
        }

        public string ExceptionToString(string side)
        {
            StringBuilder str = new StringBuilder();

            str.Append("invalid ");
            str.Append(side);
            str.Append(" operand for ");
            str.Append(Token.tokenstring(tok));

            return str.ToString();
        }
    }
}
