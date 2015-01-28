package com.rokuan.iris.exception;

public class MissingCharacterName extends BaseException {
	public MissingCharacterName(int lineNumber, int columnNumber) {
		super(lineNumber, columnNumber);
	}

	@Override
	public String ExceptionToString() {
		// TODO Auto-generated method stub
		return null;
	}	
}
