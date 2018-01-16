package com.kkpa.cubesummation.service;

import com.kkpa.cubesummation.dto.CubeDTO;
import com.kkpa.cubesummation.dto.CubeTestCasesDTO;
import com.kkpa.cubesummation.exceptions.CubeException;

public interface CubeService {

	void processCube(CubeDTO cubeDTO) throws CubeException;
	
	
	void processTestCases(CubeTestCasesDTO testCases) throws CubeException ;
	
}
