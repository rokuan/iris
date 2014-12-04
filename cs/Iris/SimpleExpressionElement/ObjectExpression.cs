using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Iris.ScriptElements.FunctionStructureElement;

namespace Iris.SimpleExpressionElement
{
    public class ObjectExpression
    {
        public ObjectType type;

        public Object value;

        //public Dictionary<string, ObjectExpression> fields;
        //public Dictionary<string, FunctionDefinition> functions;

        public ObjectExpression(ObjectType objType, Object objValue)
        {
            type = objType;
            value = objValue;
        }

        /*public ObjectExpression findObject(string name)
        {
            return fields[name];
        }

        public void addField(string fieldName, ObjectExpression obj)
        {
            if (fields == null)
            {
                fields = new Dictionary<string, ObjectExpression>();
            }

            fields.Add(fieldName, obj);
        }*/

        public override string ToString()
        {
            return value.ToString();
        }
    }
}
