package com.rokuan.iris.script;

import java.util.ArrayList;
import java.util.HashMap;

import com.rokuan.iris.expression.Expression;
import com.rokuan.iris.expression.LabelExpression;

public class NpcStructure
{
	public String name;
	public HashMap<String, Expression> labels = new HashMap<String, Expression>();
	public String location;
	public ArrayList<Integer> coordinates = new ArrayList<Integer>();

	public NpcStructure()
	{

	}

	public NpcStructure(String npcName)
	{
		name = npcName;
	}

	public void addLabel(LabelExpression exp)
	{
		addLabel(exp.labelName, exp.body);
	}

	public void addLabel(String labelName, Expression exp)
	{
		if (labels.containsKey(labelName) || exp == null)
		{
			return;
		}

		labels.put(labelName, exp);
	}
	
	public void addCordinate(int coord){
		coordinates.add(coord);
	}

	public boolean validNpc()
	{
		return labels.containsKey("main");
	}
}