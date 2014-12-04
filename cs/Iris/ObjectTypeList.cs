using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Iris
{
    //class ObjectTypeList : ObjectType
    public class ObjectTypeList
    {
        public List<ObjectType> types;

        public ObjectTypeList()
        {

        }

        public void addObjectType(ObjectType objType)
        {
            if (types == null)
            {
                types = new List<ObjectType>();
            }

            types.Add(objType);
        }
    }
}
