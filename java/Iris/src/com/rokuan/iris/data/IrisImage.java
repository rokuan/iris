package com.rokuan.iris.data;

public class IrisImage extends IrisFile
{
	private static final String baseFolder = "images/";

	public String name;

	public IrisImage()
	{

	}

	public IrisImage(String filePath)
	{
		this.setFilepath(filePath);
	}

	public IrisImage(String imageName, String filePath)
	{
		this(filePath);
		this.name = imageName;
	}
	
	@Override
	public void setFilepath(String path){
		super.setFilepath(baseFolder + path);
	}
}
