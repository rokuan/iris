using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils.LexParseException
{
    public class EmptyExpression : BaseException
    {
        public EmptyExpression(int lNum, int cNum)
            : base(lNum, cNum)
        {

        }

        public override string ExceptionToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append("empty expression: ");
            str.Append(base.stringTrace());

            return str.ToString();
        }
    }
}
