package com.kkpa.coderefactoring.dto;

import com.kkpa.coderefactoring.enums.EDriverStatus;

public class DriverDTO {

	private Long idDriver;
	
	private Long idCar;
	
	private EDriverStatus status;

	public Long getIdDriver() {
		return idDriver;
	}

	public void setIdDriver(Long idDriver) {
		this.idDriver = idDriver;
	}

	public Long getIdCar() {
		return idCar;
	}

	public void setIdCar(Long idCar) {
		this.idCar = idCar;
	}

	public EDriverStatus getStatus() {
		return status;
	}

	public void setStatus(EDriverStatus status) {
		this.status = status;
	}
	
}
