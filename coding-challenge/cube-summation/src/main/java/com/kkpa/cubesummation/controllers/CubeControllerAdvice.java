package com.kkpa.cubesummation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kkpa.cubesummation.dto.CubeTestCasesDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.exceptions.CubeException;

@RestControllerAdvice
public class CubeControllerAdvice {
	
	@Autowired
	private ApplicationContext appCtx;
	
	private Logger LOGGER = LoggerFactory.getLogger(CubeControllerAdvice.class);
	
	
	@ExceptionHandler(CubeException.class)
	@ResponseBody
	public ResponseEntity<CubeTestCasesDTO> handleRequestException(CubeException exception) {
		CubeTestCasesDTO testDTO = (CubeTestCasesDTO) appCtx.getBean("cubeTestCases");

		testDTO.setMsg(exception.getMessage());
		testDTO.setStatus(exception.getCode());
		LOGGER.error(testDTO.getStatus() + "-" + testDTO.getMsg());
		
		return new ResponseEntity<CubeTestCasesDTO>( testDTO, new HttpHeaders(), HttpStatus.OK);
	} 
	
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public ResponseEntity<CubeTestCasesDTO> handleRequestException(Throwable exception) {
		CubeTestCasesDTO testDTO = (CubeTestCasesDTO) appCtx.getBean("cubeTestCases");

		testDTO.setMsg(ECodeStatus.ERROR.getMsg());
		testDTO.setStatus(ECodeStatus.ERROR.getCode());

		// Solo por imprimir la traza.
		exception.printStackTrace();
		return new ResponseEntity<CubeTestCasesDTO>( testDTO, new HttpHeaders(), HttpStatus.OK);
	} 

}
