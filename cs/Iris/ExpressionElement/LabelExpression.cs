using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.ExpressionElement
{
    public class LabelExpression : Expression, IBlockExpression
    {

        public string labelName;
        public Expression body;

        public LabelExpression(string lName)
        {
            this.labelName = lName;
        }

        public LabelExpression(string lName, Expression lBody)
        {
            this.labelName = lName;
            this.body = lBody;
        }

        public List<string> declaredVariables()
        {
            return null;
        }
    }
}
