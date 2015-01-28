package com.rokuan.iris.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.rokuan.iris.process.IIrisStreamReader;

public class ScannerStreamReader implements IIrisStreamReader {
	private Scanner sc;

	public ScannerStreamReader(String filepath) throws FileNotFoundException {
		this.sc = new Scanner(new File(filepath));
	}

	@Override
	public boolean hasNextLine() {
		return sc.hasNextLine();
	}

	@Override
	public String nextLine() {
		return sc.nextLine();
	}

	@Override
	public void close() {
		sc.close();
	}
}
