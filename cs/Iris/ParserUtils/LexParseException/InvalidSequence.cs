using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils.LexParseException
{
    public class InvalidSequence : BaseException
    {
        public InvalidSequence(int lineNum, int colNum)
            : base(lineNum, colNum)
        {

        }

        public override string ExceptionToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append("invalid sequence: ");
            str.Append(base.stringTrace());

            return str.ToString();
        }
    }
}
