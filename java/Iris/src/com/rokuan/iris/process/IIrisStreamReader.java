package com.rokuan.iris.process;

public interface IIrisStreamReader {
	public boolean hasNextLine();
	public String nextLine();
	public void close();
}
