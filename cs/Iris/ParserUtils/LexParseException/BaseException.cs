using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils.LexParseException
{
    public abstract class BaseException : Exception
    {
        protected int lineNum;
        protected int columnNum;

        public BaseException(int lineNumber, int columnNumber)
        {
            this.lineNum = lineNumber;
            this.columnNum = columnNumber;
        }

        public string stringTrace()
        {
            StringBuilder str = new StringBuilder();

            str.Append("line ");
            str.Append(lineNum);
            str.Append(", char ");
            str.Append(columnNum);
            str.Append(' ');

            return str.ToString();
        }

        public abstract string ExceptionToString();
    }
}
