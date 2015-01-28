package com.rokuan.iris.implementation;

import java.io.FileNotFoundException;

import javax.swing.SwingUtilities;

import com.rokuan.iris.implementation.swing.EvalFrame;
import com.rokuan.iris.process.IIrisStreamReader;
import com.rokuan.iris.process.ScriptLoader;

public class Main {
	public static void main(String[] args){		
		/*SwingUtilities.invokeLater(new Runnable(){
			public void run(){*/
				HashMapResources resources = new HashMapResources();
				HashMapDataLoader data = new HashMapDataLoader();
				ScriptLoader loader = new ScriptLoader() {			
					@Override
					protected IIrisStreamReader getStreamFromFile(String filepath) {
						try {
							return new ScannerStreamReader(filepath);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						return null;
					}
				};
				
				loader.setContentsFile("content.txt");
				loader.setScriptsFile("scripts.txt");

				//long start = System.currentTimeMillis();
				loader.loadContents(resources);
				loader.loadScripts(resources);
				
				//EvalFrame eFrame = new EvalFrame(resources, loader);
				EvalFrame eFrame = new EvalFrame(resources, data);
				eFrame.createAndShowGUI();
		/*	}
		});*/
		//System.out.println("Total: " + (System.currentTimeMillis() - start) + "ms");
	}
}
