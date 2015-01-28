package com.rokuan.iris.interfaces;

import com.rokuan.iris.data.IrisBackground;
import com.rokuan.iris.data.IrisCharacter;
import com.rokuan.iris.data.IrisImage;
import com.rokuan.iris.data.IrisSound;
import com.rokuan.iris.script.NpcStructure;


public interface IIrisResources {
	public void addImage(String tag, IrisImage image);
	public void addBackground(String tag, IrisBackground background);
	public void addSound(String tag, IrisSound sound);
	//public void addVideo(String tag, IrisVideo video);
	public void addCharacter(String tag, IrisCharacter character);
	public void addNpc(String tag, NpcStructure npc);
	public void addMedia(String tag, Object media);
	
	public IrisImage getImage(String tag);
	public IrisBackground getBackground(String tag);
	public IrisSound getSound(String tag);
	//public IrisVideo getVideo(String tag);
	public IrisCharacter getCharacter(String tag);
	public NpcStructure getNpc(String tag);
	public Object getMedia(String tag, Object media);
}
