package com.kkpa.coderefactoring.dto;

import com.kkpa.coderefactoring.enums.EServiceStatus;

public class ServiceDTO {
	
	private Long id;
	
	private EServiceStatus status;
	
	private Long idDriver;
	
	private Long idCar;
	
	private Long userUiid;
	
	private String type;
	
	private UserDTO user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EServiceStatus getIdStatus() {
		return status;
	}

	public void setStatus(EServiceStatus pStatus) {
		this.status = pStatus;
	}

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

	public Long getUserUiid() {
		return userUiid;
	}

	public void setUserUiid(Long userUiid) {
		this.userUiid = userUiid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public EServiceStatus getStatus() {
		return status;
	}

	
	
	
}
