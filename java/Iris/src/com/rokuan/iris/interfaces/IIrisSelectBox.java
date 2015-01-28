package com.rokuan.iris.interfaces;

import java.util.List;

public interface IIrisSelectBox extends IIrisContentComponent {
	public int getSelectedIndex();
	public void setChoices(List<String> choices);
}
