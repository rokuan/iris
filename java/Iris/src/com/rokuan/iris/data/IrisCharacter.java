package com.rokuan.iris.data;

import java.util.HashMap;


public class IrisCharacter
{
	public String name;
	public String charName;
	//public Color color;
	public String colorHexCode;
	private HashMap<String, IrisImage> images = new HashMap<String, IrisImage>();

	public IrisCharacter()
	{

	}

	public IrisCharacter(String charName)
	{
		this.name = charName;
	}

	public void setColor(String hexColor)
	{
		/*try{
			color = Color.decode(hexColor.substring(1));
		}catch(Exception e){

		}*/
		this.colorHexCode = hexColor;
	}

	public void addImage(String name, String imagePath)
	{
		images.put(name, new IrisImage(name, imagePath));
	}

	public IrisImage getImage(String imgName)
	{
		return images.get(imgName);
	}
}
