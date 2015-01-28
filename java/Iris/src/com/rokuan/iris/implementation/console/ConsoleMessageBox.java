package com.rokuan.iris.implementation.console;

import com.rokuan.iris.interfaces.IIrisDialogBox;

public class ConsoleMessageBox implements IIrisDialogBox {

	@Override
	public void clearContent() {
		System.out.println("CLEARMSG");
	}

	@Override
	public void showComponent() {
		System.out.println("SHOWMSG");
	}

	@Override
	public void enableComponent() {
		System.out.println("+MSG");
	}

	@Override
	public void disableComponent() {
		System.out.println("-MSG");
	}

	@Override
	public void hideComponent() {
		System.out.println("HIDEMSG");
	}

	@Override
	public void removeSpeaker() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSpeaker(String name) {
		System.out.println("SPEAKER=" + name);
	}

	@Override
	public void appendText(String text) {
		System.out.println(text);
	}

}
