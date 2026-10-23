package com.kkpa.cubesummation.exceptions;

import com.kkpa.cubesummation.enums.ECodeStatus;

/**
 * Business error. It holds a fixed status and an optional detail, for example
 * the bad value found in the file.
 */
public class CubeException extends Exception {

	private static final long serialVersionUID = 8840975140051330420L;

	private final ECodeStatus status;

	public CubeException(ECodeStatus status) {
		this(status, null);
	}

	public CubeException(ECodeStatus status, String detail) {
		super(buildMessage(status, detail));
		this.status = status;
	}

	public ECodeStatus getStatus() {
		return status;
	}

	public int getCode() {
		return status.getCode();
	}

	private static String buildMessage(ECodeStatus status, String detail) {
		if (detail == null || detail.isEmpty()) {
			return status.getMsg();
		}
		return status.getMsg() + " - " + detail;
	}

}
