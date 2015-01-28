package com.rokuan.iris.exception;

public class InvalidNpcException extends BaseException {
	private String npcName;

	public InvalidNpcException(int lineNumber, int columnNumber, String nName) {
		super(lineNumber, columnNumber);
		this.npcName = nName;
	}

	@Override
	public String ExceptionToString() {
		StringBuilder str = new StringBuilder();
		
		str.append(super.stringTrace());

		str.append("missing label 'main' for npc ");
		str.append(this.npcName);

		return str.toString();

	}

}
