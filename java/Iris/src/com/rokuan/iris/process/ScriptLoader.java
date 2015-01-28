package com.rokuan.iris.process;

import com.rokuan.iris.interfaces.IIrisResources;
import com.rokuan.iris.parser.ContentParser;
import com.rokuan.iris.parser.ScriptParser;

public abstract class ScriptLoader {
	private String contentFilePath = null;
	private String scriptFilePath = null;

	public final void setContentsFile(String filepath) {
		this.contentFilePath = filepath;
	}

	public final void setScriptsFile(String filepath) {
		this.scriptFilePath = filepath;
	}

	public final void loadContents(IIrisResources resources) {
		if (this.contentFilePath == null) {
			return;
		}

		IIrisStreamReader reader = getStreamFromFile(this.contentFilePath);

		while (reader.hasNextLine()) {
			String contentFile = reader.nextLine().trim();

			if (!contentFile.startsWith("//")) {
				IIrisStreamReader contentReader = getStreamFromFile(contentFile);
				ContentParser parser = new ContentParser(contentReader,
						resources);
				try {
					parser.startProcess();
				} catch (Exception e) {
					// TODO: afficher l'exception ou la renvoyer
					e.printStackTrace();
				}
				contentReader.close();
			}
		}

		reader.close();
	}

	public final void loadScripts(IIrisResources resources) {
		if (this.scriptFilePath == null) {
			return;
		}

		IIrisStreamReader reader = getStreamFromFile(this.scriptFilePath);

		while (reader.hasNextLine()) {
			String scriptFile = reader.nextLine().trim();

			if (!scriptFile.startsWith("//")) {
				IIrisStreamReader scriptReader = getStreamFromFile(scriptFile);
				ScriptParser parser = new ScriptParser(scriptReader, resources);
				try {
					parser.startProcess();
				} catch (Exception e) {
					// TODO: afficher l'exception ou la renvoyer
					e.printStackTrace();
				}
				scriptReader.close();
			}
		}
	}

	protected abstract IIrisStreamReader getStreamFromFile(String filepath);
}
