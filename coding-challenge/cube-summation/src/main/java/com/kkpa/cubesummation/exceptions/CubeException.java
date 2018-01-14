package com.kkpa.cubesummation.exceptions;

import com.kkpa.cubesummation.enums.ECodeStatus;

public class CubeException extends Exception {

	private int code;
	
	private String msg;
	
	public CubeException (ECodeStatus code) {
		super(code.getMsg());
		this.code = code.getCode();
		this.msg = code.getMsg();		
	}
	
	public CubeException (int code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;		
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	
}
