using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.SimpleExpressionElement;

namespace Iris.ExpressionElement
{
    public class IfExpression : Expression, IBlockExpression
    {
        public SimpleExpression condition;
        public Expression body;

        public List<ElseIfExpression> elseIfList
        {
            get;
            private set;
        }

        public ElseExpression elseBlock;

        public IfExpression()
        {

        }

        public IfExpression(SimpleExpression conditionIf, Expression ifBody)
        {
            this.condition = conditionIf;
            this.body = ifBody;
        }

        public void addElseIf(ElseIfExpression elseIfExp)
        {
            if (elseIfList == null)
            {
                elseIfList = new List<ElseIfExpression>();
            }

            elseIfList.Add(elseIfExp);
        }

        public void addElse(ElseExpression elseExp)
        {
            elseBlock = elseExp;
        }

        public bool hasElseBlock()
        {
            return (elseBlock != null);
        }

        public List<string> declaredVariables()
        {
            return null;
        }        
    }
}
