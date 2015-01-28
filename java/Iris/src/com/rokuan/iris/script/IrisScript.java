package com.rokuan.iris.script;

import java.util.ArrayList;

import com.rokuan.iris.data.IrisBackground;
import com.rokuan.iris.data.IrisCharacter;
import com.rokuan.iris.data.IrisData;
import com.rokuan.iris.data.IrisImage;
import com.rokuan.iris.data.IrisSound;

public class IrisScript
{
	public ArrayList<ConstantStructure> constants;
	public ArrayList<NpcStructure> npcs;
	public ArrayList<FunctionStructure> functions;
	public ArrayList<IrisCharacter> characters;
	public ArrayList<IrisImage> images;
	public ArrayList<IrisSound> sounds;
	public ArrayList<IrisBackground> backgrounds;

	public IrisScript()
	{
		
	}

	public void addNpc(NpcStructure npc)
	{
		if (npcs == null)
		{
			npcs = new ArrayList<NpcStructure>();
		}

		npcs.add(npc);
	}

	public void addConstant(ConstantStructure constant)
	{
		if (constants == null)
		{
			constants = new ArrayList<ConstantStructure>();
		}

		constants.add(constant);
	}

	public void addFunction(FunctionStructure function)
	{
		if (functions == null)
		{
			functions = new ArrayList<FunctionStructure>();
		}

		functions.add(function);
	}

	public void addCharacter(IrisCharacter charac)
	{
		if (characters == null)
		{
			characters = new ArrayList<IrisCharacter>();
		}

		characters.add(charac);
	}

	public void addImage(IrisImage image)
	{
		if (images == null)
		{
			images = new ArrayList<IrisImage>();
		}

		images.add(image);
	}

	public void addSound(IrisSound sound)
	{
		if (sounds == null)
		{
			sounds = new ArrayList<IrisSound>();
		}

		sounds.add(sound);
	}

	public void addBackground(IrisBackground background)
	{
		if (backgrounds == null)
		{
			backgrounds = new ArrayList<IrisBackground>();
		}

		backgrounds.add(background);
	}

	public void addContent()
	{
		if (characters != null)
		{
			for (IrisCharacter charac: characters)
			{
				IrisData.addCharacter(charac.name, charac);
			}
		}

		if (images != null)
		{
			for (IrisImage image: images)
			{
				IrisData.addImage(image.name, image);
			}
		}

		if (sounds != null)
		{
			for (IrisSound sound: sounds)
			{
				IrisData.addSound(sound.name, sound);
			}
		}

		if (backgrounds != null)
		{
			for (IrisBackground background: backgrounds)
			{
				IrisData.addBackground(background.name, background);
			}
		}

		if (npcs != null)
		{
			for (NpcStructure npc: npcs)
			{
				IrisData.addNpc(npc.name, npc);
			}
		}
	}
}