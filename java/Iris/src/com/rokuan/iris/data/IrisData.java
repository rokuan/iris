package com.rokuan.iris.data;

import java.util.HashMap;

import com.rokuan.iris.script.IrisScript;
import com.rokuan.iris.script.NpcStructure;

public class IrisData
{
	public static HashMap<String, IrisCharacter> characters = new HashMap<String, IrisCharacter>();
	public static HashMap<String, IrisBackground> backgrounds = new HashMap<String, IrisBackground>();
	public static HashMap<String, IrisImage> images = new HashMap<String, IrisImage>();
	public static HashMap<String, IrisSound> sounds = new HashMap<String, IrisSound>();

	public static HashMap<String, Integer> playerVariables = new HashMap<String, Integer>();

	public static HashMap<String, NpcStructure> npcs = new HashMap<String, NpcStructure>();


	public static void addCharacter(String charName, IrisCharacter charac)
	{
		characters.put(charName, charac);
	}

	public static void addBackground(String bgdName, IrisBackground background)
	{
		backgrounds.put(bgdName, background);
	}

	public static void addImage(String imgName, IrisImage image)
	{
		images.put(imgName, image);
	}

	public static void addSound(String soundName, IrisSound sound)
	{
		sounds.put(soundName, sound);
	}

	public static void addPlayerVariable(String varName, int value)
	{
		playerVariables.put(varName, value);
	}

	public static void setPlayerVariable(String varName, int value)
	{
		playerVariables.put(varName, value);
	}

	public static int getPlayerVariable(String varName)
	{
		if (playerVariables.containsKey(varName))
		{
			return playerVariables.get(varName);
		}

		return 0;
	}

	public static void addNpc(String npcName, NpcStructure npc)
	{
		npcs.put(npcName, npc);
	}

	public static void addScript(IrisScript scr)
	{
		scr.addContent();
	}
}
