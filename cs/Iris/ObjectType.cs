using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris
{
    public class ObjectType
    {
        public static readonly ObjectType intType = new ObjectType("Int");
        public static readonly ObjectType stringType = new ObjectType("String");
        public static readonly ObjectType charType = new ObjectType("Char");
        public static readonly ObjectType booleanType = new ObjectType("Boolean");
        //public static readonly ObjectType undefinedType = new ObjectType("Undefined");
        public static readonly ObjectType undefinedType = new ObjectType(string.Empty);
        public static readonly ObjectType characterType = new ObjectType("Character");
        public static readonly ObjectType imageType = new ObjectType("Image");
        public static readonly ObjectType soundType = new ObjectType("Sound");

        public string name;

        public ObjectType()
        {

        }

        public ObjectType(string typeName)
        {
            name = typeName;
        }

        public static bool operator ==(ObjectType o1, ObjectType o2)
        {
            if (o1.name == string.Empty)
            {
                return true;
            }
            if (o2.name == string.Empty)
            {
                return true;
            }

            return o1.name.Equals(o2.name);
        }

        public static bool operator !=(ObjectType o1, ObjectType o2)
        {
            return !(o1 == o2);
        }

    }
}
