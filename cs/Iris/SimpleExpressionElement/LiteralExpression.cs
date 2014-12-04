using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.ParserUtils;

namespace Iris.SimpleExpressionElement
{
    public class LiteralExpression : SimpleExpression
    {
        public Token value;

        public LiteralExpression(Token val)
        {
            this.value = val;
        }

        public override ObjectType getExpressionType()
        {
            switch (value.type)
            {
                case Token.TokenValue.STRING:
                    return ObjectType.stringType;

                case Token.TokenValue.CHAR:
                    return ObjectType.charType;

                case Token.TokenValue.INT:
                    return ObjectType.intType;

                case Token.TokenValue.BOOLEAN:
                    return ObjectType.booleanType;
            }

            return ObjectType.undefinedType;
        }

        public override string ToString()
        {
            switch (value.type)
            {
                case Token.TokenValue.BOOLEAN:
                    return value.booleanValue.ToString().ToLower();

                case Token.TokenValue.INT:
                    return "" + value.intValue;

                case Token.TokenValue.CHAR:
                    return "'" + value.charValue + "'";

                case Token.TokenValue.STRING:
                    return '"' + value.stringValue + '"';

                default:
                    return "";
            }
        }

        /*public override string ToString(string alinea)
        {
            return this.ToString();
        }*/
    }
}
