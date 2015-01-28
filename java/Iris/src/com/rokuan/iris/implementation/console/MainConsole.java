package com.rokuan.iris.implementation.console;

import java.io.FileNotFoundException;
import java.util.Scanner;

import com.rokuan.iris.events.EvalListener;
import com.rokuan.iris.implementation.HashMapDataLoader;
import com.rokuan.iris.implementation.HashMapResources;
import com.rokuan.iris.implementation.ScannerStreamReader;
import com.rokuan.iris.process.IIrisStreamReader;
import com.rokuan.iris.process.ScriptEval;
import com.rokuan.iris.process.ScriptLoader;

public class MainConsole {
	public static void main(String[] args) {
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

		// long start = System.currentTimeMillis();
		loader.loadContents(resources);
		loader.loadScripts(resources);

		ConsoleBackground background = new ConsoleBackground();
		ConsoleImage image = new ConsoleImage();
		ConsoleInputBox input = new ConsoleInputBox();
		ConsoleMenu menu = new ConsoleMenu();
		ConsoleMessageBox messageBox = new ConsoleMessageBox();

		ScriptEval eval = new ScriptEval(data, resources, messageBox, input,
				menu, image, image, image, background, null, null);
		eval.talkToNpc("Chastel", new EvalListener() {
			
			@Override
			public void onEvalEnd() {
				System.out.println("END OF EVAL");
				System.exit(0);
			}
		});

		Scanner sc = new Scanner(System.in);

		try {
			while (true) {
				String line = sc.nextLine();

				if (line.equals("NEXT") || line.equals("CLOSE")) {
					eval.nextMessage();
				} else {
					String[] fields = line.split("=");

					if (fields[0].equals("MENU")) {
						menu.setSelectedIndex(Integer.parseInt(fields[1]));
						eval.validateSelection();
					} else if (fields[0].equals("INPUT")) {
						input.setValue(fields[1]);
						eval.validateInput();
					}
				}
			}
		} catch (Exception e) {

		} finally {
			sc.close();
		}
	}
}
