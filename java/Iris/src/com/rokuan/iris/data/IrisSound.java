package com.rokuan.iris.data;

public class IrisSound extends IrisFile
{
	private static final String baseFolder = "sounds/";
	
	public String name;

	public IrisSound()
	{

	}

	public IrisSound(String filepath)
	{
		this.setFilepath(filepath);
	}

	public IrisSound(String soundName, String filepath)
	{
		this(filepath);
		this.name = soundName;
	}

	@Override
	public void setFilepath(String path){
		super.setFilepath(baseFolder + path);
	}
}
