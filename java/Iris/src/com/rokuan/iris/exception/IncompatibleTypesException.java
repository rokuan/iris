package com.rokuan.iris.exception;

import com.rokuan.iris.types.ObjectType;

public class IncompatibleTypesException extends BaseException
{
	public ObjectType expectedType;
	public ObjectType givenType;

	public IncompatibleTypesException(int lNum, int cNum, ObjectType expected, ObjectType given)
	{
		super(lNum, cNum);
		expectedType = expected;
		givenType = given;
	}

	public String ExceptionToString()
	{
		StringBuilder str = new StringBuilder();
		
		str.append(super.stringTrace());

		str.append("incompatible types, expected ");
		str.append(expectedType.name);
		str.append(" but ");
		str.append(givenType.name);
		str.append(" given");

		return str.toString();
	}
}
