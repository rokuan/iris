package com.rokuan.iris.exception;

public class NoPreviousTokenException extends BaseException {
	public NoPreviousTokenException(int lineNumber, int columnNumber) {
		super(lineNumber, columnNumber);
	}

	@Override
	public String ExceptionToString() {
		// TODO Auto-generated method stub
		return "";
	}
}
