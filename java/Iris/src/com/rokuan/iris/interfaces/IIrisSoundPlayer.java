package com.rokuan.iris.interfaces;

import com.rokuan.iris.data.IrisSound;

public interface IIrisSoundPlayer {
	public void playSound();
	public void setSource(IrisSound sound);
	public void stopSound();
}
