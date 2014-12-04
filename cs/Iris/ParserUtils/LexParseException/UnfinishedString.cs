using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils.LexParseException
{
    public class UnfinishedString : BaseException
    {
        public UnfinishedString(int lineNum, int colNum)
            : base(lineNum, colNum)
        {

        }

        public override string ExceptionToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append("unfinished string: started at ");
            str.Append(base.stringTrace());

            return str.ToString();
        }
    }
}
