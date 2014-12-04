using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils.LexParseException
{
    public class UndefinedException : BaseException
    {
        public UndefinedException(int lNum, int cNum)
            : base(lNum, cNum)
        {

        }

        public override string ExceptionToString()
        {
            return base.stringTrace();
        }
    }
}
