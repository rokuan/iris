using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ParserUtils.LexParseException
{
    public class IncompatibleTypesException : BaseException
    {
        public ObjectType expectedType;
        public ObjectType givenType;

        public IncompatibleTypesException(int lNum, int cNum, ObjectType expected, ObjectType given)
            : base(lNum, cNum)
        {
            expectedType = expected;
            givenType = given;
        }

        public override string ExceptionToString()
        {
            StringBuilder str = new StringBuilder();

            str.Append("incompatible types, expected ");
            str.Append(expectedType.name);
            str.Append(" but ");
            str.Append(givenType.name);
            str.Append(" given");

            return str.ToString();
        }
    }
}
