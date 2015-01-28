package com.rokuan.iris.expression;

import com.rokuan.iris.types.ObjectType;

//tab["nom case"]

public class MemberAccessExpression extends PathExpression
{
	//public ObjectType memberType;
	public String name;
	public String field;

	public MemberAccessExpression(String varName, String fieldName){
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

	public ObjectType getExpressionType()
	{
		/*if (memberType != null)
            {
                return memberType;
            }

            return ObjectType.undefinedType;*/

		return ObjectType.imageType;
	}
}
