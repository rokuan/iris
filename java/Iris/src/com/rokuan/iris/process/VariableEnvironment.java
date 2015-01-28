package com.rokuan.iris.process;

import java.util.HashMap;

import com.rokuan.iris.expression.ObjectExpression;

public class VariableEnvironment {
	private HashMap<String, ObjectExpression> variables = new HashMap<String, ObjectExpression>();
	
	public void addVariable(String name, ObjectExpression value){
		this.variables.put(name, value);
	}
	
	public boolean containsVariable(String name){
		return this.variables.containsKey(name);
	}
	
	public ObjectExpression getVariable(String name){
		return this.variables.get(name);
	}
}
