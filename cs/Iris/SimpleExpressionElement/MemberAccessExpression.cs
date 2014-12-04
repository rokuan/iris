using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris.SimpleExpressionElement
{
    //tab["nom case"]

    public class MemberAccessExpression : PathExpression
    {
        //public ObjectType memberType;

        public string name;
        public string field;

        public MemberAccessExpression(string varName, string fieldName){
            name = varName;
            field = fieldName;
        }

        /*public override string ToString()
        {
            return name + '[' + field + ']';
        }

        public override string ToString(string alinea)
        {
            return name + '[' + field + ']';
        }*/

        public override ObjectType getExpressionType()
        {
            /*if (memberType != null)
            {
                return memberType;
            }

            return ObjectType.undefinedType;*/

            return ObjectType.imageType;
        }
    }
}
