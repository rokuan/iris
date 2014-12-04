using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils.LexParseException
{
    class UnfinishedCommentary : BaseException
    {
        public UnfinishedCommentary(int lineNum, int colNum)
            : base(lineNum, colNum)
        {

        }

        public override string ExceptionToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append("unfinished commentary: started at ");
            str.Append(base.stringTrace());

            return str.ToString();
        }
    }
}
