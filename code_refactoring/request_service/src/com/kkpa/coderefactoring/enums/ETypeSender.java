package com.kkpa.coderefactoring.enums;

public enum ETypeSender {

	NO_ONE(0),
	IPHONE(1),
	ANDROID(2)
	;
	
	private int code;
	
	private ETypeSender(int pCode) {
		this.code = pCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
