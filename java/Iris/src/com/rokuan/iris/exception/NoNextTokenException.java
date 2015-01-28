package com.rokuan.iris.exception;

public class NoNextTokenException extends BaseException {
	public NoNextTokenException(int lineNumber, int columnNumber) {
		super(lineNumber, columnNumber);
	}

	@Override
	public String ExceptionToString() {
		// TODO Auto-generated method stub
		return "";
	}
}
