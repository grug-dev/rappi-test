package com.kkpa.cubesummation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kkpa.cubesummation.dto.CubeTestCasesDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.exceptions.CubeException;

/**
 * Turns errors into a CubeTestCasesDTO with the error status and message.
 *
 * The HTTP status is always 200 on purpose. The bundled Angular front shows
 * "service unavailable" for any non-200 response, and it reads the real
 * status from the "status" field of the body. Do not change this without
 * changing the front.
 */
@RestControllerAdvice
public class CubeControllerAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(CubeControllerAdvice.class);

	@ExceptionHandler(CubeException.class)
	public ResponseEntity<CubeTestCasesDTO> handleCubeException(CubeException exception) {
		LOGGER.warn("{} - {}", exception.getCode(), exception.getMessage());
		return ResponseEntity.ok(errorBody(exception.getCode(), exception.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<CubeTestCasesDTO> handleUnexpectedException(Exception exception) {
		LOGGER.error("Unexpected error while processing the file", exception);
		return ResponseEntity.ok(errorBody(ECodeStatus.ERROR.getCode(), ECodeStatus.ERROR.getMsg()));
	}

	private static CubeTestCasesDTO errorBody(int status, String msg) {
		CubeTestCasesDTO body = new CubeTestCasesDTO();
		body.setStatus(status);
		body.setMsg(msg);
		return body;
	}

}
