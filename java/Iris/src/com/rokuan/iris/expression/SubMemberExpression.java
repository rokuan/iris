package com.rokuan.iris.expression;

import com.rokuan.iris.types.ObjectType;

class SubMemberExpression extends PathExpression
{
	public String name;
    public PathExpression next;

    public SubMemberExpression()
    {

    }

    public SubMemberExpression(String memberName)
    {
        name = memberName;
    }

    public SubMemberExpression(String memberName, PathExpression nextMember)
    {
        name = memberName;
        next = nextMember;
    }

    /*public override string ToString()
    {
        return name + '.' + next.ToString();
    }

    public override string ToString(string alinea)
    {
        return name + '.' + next.ToString();
    }*/

	@Override
    public ObjectType getExpressionType()
    {
        return next.getExpressionType();
    }
}
