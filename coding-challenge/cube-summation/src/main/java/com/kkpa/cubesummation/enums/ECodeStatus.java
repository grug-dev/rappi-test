package com.kkpa.cubesummation.enums;

/**
 * Fixed status codes and messages. The values are shared by all requests, so
 * they must never change. Put request details in CubeException instead.
 */
public enum ECodeStatus {

	OK(200 , "El archivo ha sido procesado exitosamente."),
	ERROR(500, "Ha ocurrido un error al procesar el archivo."),

	OPERATION_UNAUTHORIZED(310, "Operacion no permitida"),
	MATRIX_ERROR(311,"No se pudo obtener el número que define la matrix."),
	UPDATE_RULE_LENGTH_ERROR(312,"La operación update debe estar compuesta por 5 elementos."),
	QUERY_RULE_LENGTH_ERROR(313,"La operación query debe estar compuesta por 7 elementos."),
	UPDATE_OPERATION(314,"Se esperaba una operación UPDATE"),
	QUERY_OPERATION(315,"Se esperaba una operación QUERY"),
	OPERATION_ERROR(316,"No se pudo obtener el número operaciones."),
	TEST_CASES_ERROR(317,"No se pudo obtener el número de test cases."),
	NUMBER_ERROR(318,"Los valores de la operación deben ser números enteros."),
	EMPTY_FILE(319,"El archivo está vacío"),
	INCOMPLETE_FILE(320,"El archivo no tiene todas las operaciones esperadas."),
	COORDINATE_ERROR(321,"Las coordenadas deben ser mayores o iguales a 1.")
	;

	private final int code;

	private final String msg;


	private ECodeStatus(int pCode, String pMsg) {
		this.code = pCode;
		this.msg = pMsg;
	}


	public int getCode() {
		return code;
	}


	public String getMsg() {
		return msg;
	}


}
