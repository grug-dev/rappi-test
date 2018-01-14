package com.kkpa.cubesummation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kkpa.cubesummation.dto.CubeDTO;
import com.kkpa.cubesummation.dto.CubeTestCases;
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
	public void processCube(CubeDTO cubeDTO) throws CubeException {
		cubeDTO.getOperations().stream().forEach( oper -> {
				try {
					ctxCube.process(oper , cubeDTO.getMapCube());
					if (oper.getStatus() == ECodeStatus.OK.getCode()) {
						if (oper.getType().equals(ETypeOperation.QUERY)) {
							cubeDTO.getResult().add(Long.parseLong(oper.getResult()));
						}
					}					
				} catch (CubeException e) {
					e.printStackTrace();
				}
		});
		
	}

	@Override
	public void processTestCases(CubeTestCases testCases) {
		if (testCases == null || testCases.getLstCases().isEmpty()) {
			testCases.setStatus(ECodeStatus.ERROR.getCode());
			return;
		}
		
		testCases.getLstCases().stream().forEach(cube -> {
			try {
				processCube(cube);
				cube.getResult().forEach( result -> {
					testCases.getLstResults().add(result);
				});				
			} catch (CubeException e) {
				e.printStackTrace();
			}
		});
		testCases.setStatus(ECodeStatus.OK.getCode());
		testCases.setMsg(ECodeStatus.OK.getMsg());
		
	}
	

}
