package com.rokuan.iris.implementation.console;

import com.rokuan.iris.data.IrisBackground;
import com.rokuan.iris.interfaces.IIrisBackgroundFrame;

public class ConsoleBackground implements IIrisBackgroundFrame {

	@Override
	public void setSource(IrisBackground src) {
		System.out.println("BGD=" + src.getFilepath());	
	}

	@Override
	public void removeSource() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showComponent() {
		System.out.println("SHOWBGD");
	}

	@Override
	public void enableComponent() {
		System.out.println("+BGD");
	}

	@Override
	public void disableComponent() {
		System.out.println("-BGD");
	}

	@Override
	public void hideComponent() {
		System.out.println("HIDEBGD");
	}

}
