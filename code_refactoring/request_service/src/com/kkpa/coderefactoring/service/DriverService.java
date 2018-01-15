package com.kkpa.coderefactoring.service;

import com.kkpa.coderefactoring.dto.DriverDTO;

public interface DriverService {

	DriverDTO find (Long idDriver);
	
	boolean isAvailable(DriverDTO driver);
	
	void update (DriverDTO driver);
	
}
