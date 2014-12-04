using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils.LexParseException
{
    class UnknownField : BaseException
    {
        public string fieldName;
        public string charName;

        public UnknownField(int lNum, int cNum, string cName, string field)
            : base(lNum, cNum)
        {
            charName = cName;
            fieldName = field;
        }

        public override string ExceptionToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append("unknown field '");
            str.Append(fieldName);
            str.Append("' in ");
            str.Append(charName);
            str.Append(base.stringTrace());

            return str.ToString();
        }
    }
}
