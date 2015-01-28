package com.rokuan.iris.interfaces;

public interface IIrisMediaComponent<SourceType> extends IIrisComponent {
	public void setSource(SourceType src);
	public void removeSource();
}
