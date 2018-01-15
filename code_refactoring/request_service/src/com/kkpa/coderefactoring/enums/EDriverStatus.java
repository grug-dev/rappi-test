package com.kkpa.coderefactoring.enums;

public enum EDriverStatus {	
	ASSIGNED(0),
	NOT_AVAILABLE(1);
	
	
	
	private int code;

	private EDriverStatus(int pCode) {
		this.code = pCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
