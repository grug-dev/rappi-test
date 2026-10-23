package com.kkpa.cubesummation.service;

import org.springframework.stereotype.Service;

import com.kkpa.cubesummation.aop.LogExecutionTime;
import com.kkpa.cubesummation.dto.CubeDTO;
import com.kkpa.cubesummation.dto.CubeTestCasesDTO;
import com.kkpa.cubesummation.dto.OperationDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.enums.ETypeOperation;
import com.kkpa.cubesummation.exceptions.CubeException;
import com.kkpa.cubesummation.logic.ContextCubeStrategy;

/**
 * Runs the operations of each test case and collects the QUERY results.
 */
@Service
public class CubeServiceImpl implements CubeService {

	private final ContextCubeStrategy ctxCube;

	public CubeServiceImpl(ContextCubeStrategy ctxCube) {
		this.ctxCube = ctxCube;
	}

	@Override
	@LogExecutionTime
	public void processCube(CubeDTO cubeDTO) throws CubeException {
		for (OperationDTO oper : cubeDTO.getOperations()) {
			ctxCube.process(oper, cubeDTO.getMapCube());
			if (oper.getType() == ETypeOperation.QUERY) {
				cubeDTO.getResult().add(Long.parseLong(oper.getResult()));
			}
		}
	}

	@Override
	@LogExecutionTime
	public void processTestCases(CubeTestCasesDTO testCases) throws CubeException {
		if (testCases == null || testCases.getLstCases().isEmpty()) {
			throw new CubeException(ECodeStatus.TEST_CASES_ERROR);
		}

		for (CubeDTO cube : testCases.getLstCases()) {
			processCube(cube);
			testCases.getLstResults().addAll(cube.getResult());
		}
		testCases.setStatus(ECodeStatus.OK.getCode());
		testCases.setMsg(ECodeStatus.OK.getMsg());
	}

}
