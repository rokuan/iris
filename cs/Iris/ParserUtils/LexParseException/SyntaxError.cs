using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils.LexParseException
{
    public class SyntaxError : BaseException
    {
        public Token.TokenValue tok;

        public SyntaxError(int lineNumber, int colNumber, Token.TokenValue expectedTok) : base(lineNumber, colNumber)
        {
            tok = expectedTok;
        }

        public override string ExceptionToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append("syntax error: ");
            str.Append(base.stringTrace());
            str.Append("expected ");
            str.Append(Token.tokenstring(tok));

            return str.ToString();
        }
    }
}
