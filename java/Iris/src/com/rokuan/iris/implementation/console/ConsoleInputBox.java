package com.rokuan.iris.implementation.console;

import com.rokuan.iris.enums.InputType;
import com.rokuan.iris.interfaces.IIrisInputBox;

public class ConsoleInputBox implements IIrisInputBox {
	private String value = "";
	private InputType inputType = InputType.INPUT_STRING_TYPE;

	@Override
	public void clearContent() {
		this.value = "";
	}

	@Override
	public void showComponent() {
		System.out.println("SHOWINPUT");
	}

	@Override
	public void enableComponent() {
		System.out.println("+INPUT");
	}

	@Override
	public void disableComponent() {
		System.out.println("-INPUT");
	}

	@Override
	public void hideComponent() {
		System.out.println("HIDEINPUT");
	}

	@Override
	public void setInputType(InputType type) {
		this.inputType = type;
	}

	@Override
	public InputType getInputType() {
		return this.inputType;
	}

	@Override
	public int getIntValue() {
		return Integer.parseInt(this.value);
	}

	@Override
	public String getValue() {
		return this.value;
	}

	public void setValue(String value){
		this.value = value;
	}
}
