package com.kkpa.coderefactoring.enums;

public enum EServiceStatus {

	ASSIGNED(2)	
	;
	
	private int code;
	
	private EServiceStatus(int pCode) {
		this.code = pCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
