using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils.LexParseException
{
    public class DefaultCaseException : BaseException
    {
        public DefaultCaseException(int lNum, int cNum)
            : base(lNum, cNum)
        {

        }

        public override string ExceptionToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append(base.stringTrace());
            str.Append("default case already exists");

            return str.ToString();
        }
    }
}
