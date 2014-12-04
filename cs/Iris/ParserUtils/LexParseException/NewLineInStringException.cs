using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils.LexParseException
{
    public class NewLineInstringException : BaseException
    {
        public NewLineInstringException(int lNum, int cNum)
            : base(lNum, cNum)
        {

        }

        public override string ExceptionToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append("unexpected new line in string: ");
            str.Append(base.stringTrace());

            return str.ToString();
        }
    }
}
