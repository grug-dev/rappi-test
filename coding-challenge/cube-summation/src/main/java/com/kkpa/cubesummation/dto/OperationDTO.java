package com.kkpa.cubesummation.dto;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.enums.ETypeOperation;

@Component("operationDTO")
@Scope("prototype")
public class OperationDTO {

	private String operation;
	
	private ETypeOperation type;
	
	private int status = ECodeStatus.OK.getCode();
	
	private String result = "0";
	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ETypeOperation getType() {
		return type;
	}

	public void setType(ETypeOperation type) {
		this.type = type;
	}
	
}
