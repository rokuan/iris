using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils.LexParseException
{
    public class CaseException : BaseException
    {
        public CaseException(int lNum, int cNum)
            : base(lNum, cNum)
        {
            
        }

        public override string ExceptionToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append(base.stringTrace());
            str.Append("case expression appears after default expression");

            return str.ToString();
        }
    }
}
