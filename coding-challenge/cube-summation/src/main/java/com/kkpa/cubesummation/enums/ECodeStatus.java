package com.kkpa.cubesummation.enums;

public enum ECodeStatus {

	OK(200 , "El archivo ha sido procesado exitosamente."),
	ERROR(500, "Ha ocurrido un error al procesar el archivo."),
	
	OPERATION_UNAUTHORIZED(310, "Operacion no permitida"),
	MATRIX_ERROR(311,"No se pudo obtener el número que define la matrix. "),
	OPERATION_ERROR(311,"No se pudo obtener el número operaciones. "),
	;
	
	private int code;
	
	private String msg;
	
	
	private ECodeStatus(int pCode, String pMsg) {
		this.code = pCode;
		this.msg = pMsg;
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
