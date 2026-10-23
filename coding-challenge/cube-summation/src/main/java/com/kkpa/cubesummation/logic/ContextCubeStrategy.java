package com.kkpa.cubesummation.logic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.kkpa.cubesummation.dto.OperationDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.enums.ETypeOperation;
import com.kkpa.cubesummation.exceptions.CubeException;

@Component("ctxCubeStrategy")
public class ContextCubeStrategy {

	private CubeStrategy strategy;

	@Autowired
	private ApplicationContext appCtx;

	public ContextCubeStrategy() {
	}

	public void process(OperationDTO operationDTO, Map<String, Long> mapCube) throws CubeException {
		String strategyName = null;
		
		if (operationDTO.getOperation().startsWith(ETypeOperation.UPDATE.toString())) {
			strategyName = "updateCubeStrategy";
		}
		else if (operationDTO.getOperation().startsWith(ETypeOperation.QUERY.toString())) {
			strategyName = "queryCubeStrategy";
		}
		if (strategyName == null) {
			throw new CubeException(ECodeStatus.OPERATION_UNAUTHORIZED, operationDTO.getOperation());
		}
		
		this.strategy = (CubeStrategy) appCtx.getBean(strategyName);
		this.strategy.process(operationDTO, mapCube);
	}
}
