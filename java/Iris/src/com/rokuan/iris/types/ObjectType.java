package com.rokuan.iris.types;

public class ObjectType
{
	public static final ObjectType intType = new ObjectType("Int");
	public static final ObjectType stringType = new ObjectType("String");
	public static final ObjectType charType = new ObjectType("Char");
	public static final ObjectType booleanType = new ObjectType("Boolean");
	//public static final ObjectType undefinedType = new ObjectType("Undefined");
	public static final ObjectType undefinedType = new ObjectType("");
	public static final ObjectType characterType = new ObjectType("Character");
	public static final ObjectType imageType = new ObjectType("Image");
	public static final ObjectType soundType = new ObjectType("Sound");

	public String name;

	public ObjectType()
	{

	}

	public ObjectType(String typeName)
	{
		name = typeName;
	}

	@Override
	public boolean equals(Object o)
	{
		if(o == null){
			return false;
		}
		
		if(this == o){
			return true;
		}
		
		ObjectType ot = (ObjectType)o;

		if (this.name.isEmpty())
		{
			return true;
		}
		if (ot.name.isEmpty())
		{
			return true;
		}

		return this.name.equals(ot.name);
	}
}
