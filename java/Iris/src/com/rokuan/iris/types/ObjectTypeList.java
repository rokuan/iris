package com.rokuan.iris.types;

import java.util.ArrayList;

//class ObjectTypeList : ObjectType
public class ObjectTypeList
{
	public ArrayList<ObjectType> types;

	public ObjectTypeList()
	{

	}

	public void addObjectType(ObjectType objType)
	{
		if (types == null)
		{
			types = new ArrayList<ObjectType>();
		}

		types.add(objType);
	}
}
