package com.rokuan.iris.data;

public abstract class IrisFile {
	private String filepath;
	
	public void setFilepath(String path){
		this.filepath = path;
	}
	
	public String getFilepath(){
		return this.filepath;
	}
}
