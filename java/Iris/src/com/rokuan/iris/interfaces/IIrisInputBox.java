package com.rokuan.iris.interfaces;

import com.rokuan.iris.enums.InputType;

public interface IIrisInputBox extends IIrisContentComponent {
	public void setInputType(InputType type);
	public InputType getInputType();
	public int getIntValue();
	public String getValue();
}
