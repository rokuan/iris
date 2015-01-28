package com.rokuan.iris.implementation.console;

import java.util.List;

import com.rokuan.iris.interfaces.IIrisSelectBox;

public class ConsoleMenu implements IIrisSelectBox {
	private List<String> choices;
	private int index = -1;

	@Override
	public void clearContent() {
		index = -1;
	}

	@Override
	public void showComponent() {
		System.out.println("SHOWMENU");
	}

	@Override
	public void enableComponent() {
		System.out.println("+MENU");
	}

	@Override
	public void disableComponent() {
		System.out.println("-MENU");
	}

	@Override
	public void hideComponent() {
		System.out.println("HIDEMENU");
	}

	@Override
	public int getSelectedIndex() {
		return this.index;
	}

	public void setSelectedIndex(int selected) {
		this.index = selected;
	}

	@Override
	public void setChoices(List<String> choices) {
		this.choices = choices;

		int i = 0;

		for (String s : this.choices) {
			System.out.println(i + " - " + s);
			i++;
		}
	}

}
