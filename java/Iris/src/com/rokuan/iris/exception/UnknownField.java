package com.rokuan.iris.exception;

public class UnknownField extends BaseException
{
	public String fieldName;
	public String charName;

	public UnknownField(int lNum, int cNum, String cName, String field)
	{
		super(lNum, cNum);
		charName = cName;
		fieldName = field;
	}

	public String ExceptionToString()
	{
		StringBuilder str = new StringBuilder();

		str.append("unknown field '");
		str.append(fieldName);
		str.append("' in ");
		str.append(charName);
		str.append(super.stringTrace());

		return str.toString();
	}
}
