using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.ExpressionElement.Instruction;

namespace Iris.SimpleExpressionElement
{
    /*class MethodCallExpression : SimpleExpression, IInstruction
    {
        public ObjectType methodType = null;

        public List<SimpleExpression> args
        {
            get;
            private set;
        }

        public string name
        {
            get;
            private set;
        }

        public MethodCallExpression(string funcName)
        {
            name = funcName;
        }

        public void addArgument(SimpleExpression se)
        {
            if (args == null)
            {
                args = new List<SimpleExpression>();
            }

            args.Add(se);
        }

        /*public override string ToString(string alinea)
        {
            StringBuilder str = new StringBuilder();

            str.Append(alinea);
            str.Append(name);

            str.Append('(');

            if (args != null)
            {
                str.Append(args.ElementAt(0));

                for (int i = 1; i < args.Count; i++)
                {
                    str.Append(',');
                    str.Append(args.ElementAt(i).ToString());
                }
            }

            str.Append(')');

            return str.ToString();
        }

        public override ObjectType getExpressionType()
        {
            if (methodType != null)
            {
                return methodType;
            }

            return ObjectType.undefinedType;
        }
    }*/
}
