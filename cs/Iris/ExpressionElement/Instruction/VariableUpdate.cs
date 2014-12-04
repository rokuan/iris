using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.SimpleExpressionElement;
using Iris.ParserUtils;

namespace Iris.ExpressionElement.Instruction
{
    public class VariableUpdate : Expression, IInstruction
    {
        public PathExpression variable;

        public Token.TokenValue op;

        public VariableUpdate(PathExpression varPath, Token.TokenValue opUpdate)
        {
            this.variable = varPath;
            this.op = opUpdate;
        }
    }
}
