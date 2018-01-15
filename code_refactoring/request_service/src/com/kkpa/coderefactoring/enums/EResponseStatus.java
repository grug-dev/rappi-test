package com.kkpa.coderefactoring.enums;

public enum EResponseStatus {

	OK(0),
	NOT_EXIST(3),
	NOT_AVAILABLE(2),
	NOTIFICATION_ERROR(3)
	;
	
	private int code;
	
	private EResponseStatus(int pCode) {
		this.code = pCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
