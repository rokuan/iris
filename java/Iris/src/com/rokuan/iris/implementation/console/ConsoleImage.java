package com.rokuan.iris.implementation.console;

import com.rokuan.iris.data.IrisImage;
import com.rokuan.iris.interfaces.IIrisImagePanel;

public class ConsoleImage implements IIrisImagePanel {

	@Override
	public void setSource(IrisImage src) {
		System.out.println("IMG=" + src.getFilepath());
	}

	@Override
	public void removeSource() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showComponent() {
		
	}

	@Override
	public void enableComponent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableComponent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideComponent() {
		// TODO Auto-generated method stub
		
	}

}
