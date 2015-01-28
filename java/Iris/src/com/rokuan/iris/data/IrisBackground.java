package com.rokuan.iris.data;

public class IrisBackground extends IrisFile
{
	private static final String baseFolder = "backgrounds/";
	
	public String name;
	public boolean stretch = true;

	public IrisBackground()
	{
		
	}

	public IrisBackground(String bgName, String filePath)
	{
		this.name = bgName;
		this.setFilepath(filePath);
	}
	
	@Override
	public void setFilepath(String path) {
		super.setFilepath(baseFolder + path);
	}	
}
