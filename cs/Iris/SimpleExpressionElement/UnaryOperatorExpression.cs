using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.ParserUtils;
using Iris.ExpressionElement;

namespace Iris.SimpleExpressionElement
{
    public class UnaryOperatorExpression : SimpleExpression
    {
        public Token.TokenValue op;
        public SimpleExpression exp;

        public UnaryOperatorExpression(Token.TokenValue operatorToken, SimpleExpression expr)
        {
            op = operatorToken;
            exp = expr;
        }

        public override ObjectType getExpressionType()
        {
            if (op == Token.TokenValue.NEG)
            {
                return ObjectType.booleanType;
            }

            return ObjectType.undefinedType;
        }
    }
}
