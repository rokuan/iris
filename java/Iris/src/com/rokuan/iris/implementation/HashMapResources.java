package com.rokuan.iris.implementation;

import java.util.HashMap;

import com.rokuan.iris.data.IrisBackground;
import com.rokuan.iris.data.IrisCharacter;
import com.rokuan.iris.data.IrisImage;
import com.rokuan.iris.data.IrisSound;
import com.rokuan.iris.interfaces.IIrisResources;
import com.rokuan.iris.script.NpcStructure;

public class HashMapResources implements IIrisResources {
	private HashMap<String, IrisBackground> backgrounds = new HashMap<String, IrisBackground>();
    private HashMap<String, IrisImage> images = new HashMap<String, IrisImage>();
    private HashMap<String, IrisSound> sounds = new HashMap<String, IrisSound>();
    private HashMap<String, IrisCharacter> characters = new HashMap<String, IrisCharacter>();
    private HashMap<String, NpcStructure> npcs = new HashMap<String, NpcStructure>();

    @Override
    public void addImage(String s, IrisImage irisImage) {
        images.put(s, irisImage);
    }

    @Override
    public void addBackground(String s, IrisBackground irisBackground) {
        backgrounds.put(s, irisBackground);
    }

    @Override
    public void addSound(String s, IrisSound irisSound) {
        sounds.put(s, irisSound);
    }

    @Override
    public void addCharacter(String s, IrisCharacter irisCharacter) {
        characters.put(s, irisCharacter);
    }

    @Override
    public void addNpc(String s, NpcStructure npcStructure) {
        npcs.put(s, npcStructure);
    }

    @Override
    public void addMedia(String s, Object o) {

    }

    @Override
    public IrisImage getImage(String s) {
        return images.get(s);
    }

    @Override
    public IrisBackground getBackground(String s) {
        return backgrounds.get(s);
    }

    @Override
    public IrisSound getSound(String s) {
        return sounds.get(s);
    }

    @Override
    public IrisCharacter getCharacter(String s) {
        return characters.get(s);
    }

    @Override
    public NpcStructure getNpc(String s) {
        return npcs.get(s);
    }

    @Override
    public Object getMedia(String s, Object o) {
        return null;
    }
}
