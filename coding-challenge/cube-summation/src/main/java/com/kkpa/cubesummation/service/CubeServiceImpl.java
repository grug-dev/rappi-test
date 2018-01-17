package com.kkpa.cubesummation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kkpa.cubesummation.aop.LogExecutionTime;
import com.kkpa.cubesummation.dto.CubeDTO;
import com.kkpa.cubesummation.dto.CubeTestCasesDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.enums.ETypeOperation;
import com.kkpa.cubesummation.exceptions.CubeException;
import com.kkpa.cubesummation.logic.ContextCubeStrategy;

@Service("cubeSrv")
public class CubeServiceImpl implements CubeService {

	
	@Autowired
	@Qualifier("ctxCubeStrategy")
	private ContextCubeStrategy  ctxCube;
	
	@Override
	@LogExecutionTime
	public void processCube(CubeDTO cubeDTO) throws CubeException {
		try {
			cubeDTO.getOperations().stream().forEach( oper -> {
				try {
					ctxCube.process(oper , cubeDTO.getMapCube());
					if (oper.getStatus() == ECodeStatus.OK.getCode()) {
						if (oper.getType().equals(ETypeOperation.QUERY)) {
							cubeDTO.getResult().add(Long.parseLong(oper.getResult()));
						}
					}					
				} catch (CubeException e) {
					throw new RuntimeException(e);
				}
		});
		}catch (Exception ex) {
			if (ex.getCause() instanceof CubeException) {
				CubeException cE = (CubeException) ex.getCause();
				throw cE;
			}
			else {
				throw ex;
			}
		}
		
	}

	@Override
	@LogExecutionTime
	public void processTestCases(CubeTestCasesDTO testCases) throws CubeException {
		if (testCases == null || testCases.getLstCases().isEmpty()) {
			testCases.setStatus(ECodeStatus.ERROR.getCode());
			return;
		}
		
		try {
			testCases.getLstCases().stream().forEach(cube -> {
				try {
					processCube(cube);
					cube.getResult().forEach( result -> {
						testCases.getLstResults().add(result);
					});				
				}  catch (CubeException e) {
					throw new RuntimeException(e);
				}
			});
			testCases.setStatus(ECodeStatus.OK.getCode());
			testCases.setMsg(ECodeStatus.OK.getMsg());
		}
		catch (Exception ex) {
			if (ex.getCause() instanceof CubeException) {
				CubeException cE = (CubeException) ex.getCause();
				throw cE;
			}
			else {
				throw ex;
			}
		}
		
	}
	

}
