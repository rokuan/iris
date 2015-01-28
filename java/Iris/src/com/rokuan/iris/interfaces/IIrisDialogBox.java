package com.rokuan.iris.interfaces;

public interface IIrisDialogBox extends IIrisContentComponent {
	public void removeSpeaker();
	public void setSpeaker(String name);
	/**
	 * Appends the text to the dialog area
	 * @param text
	 */
	public void appendText(String text);
}
