package com.kkpa.cubesummation.logic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.kkpa.cubesummation.dto.OperationDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.exceptions.CubeException;

@Component("ctxCubeStrategy")
public class ContextCubeStrategy {

	private CubeStrategy strategy;

	private static final String UPDATE = "UPDATE";

	private static final String QUERY = "QUERY";

	@Autowired
	private ApplicationContext appCtx;

	public ContextCubeStrategy() {
	}

	public void process(OperationDTO operationDTO, Map<String, Long> mapCube) throws CubeException {
		String strategyName = null;
		
		if (operationDTO.getOperation().startsWith(UPDATE)) {
			strategyName = "updateCubeStrategy";
		}
		else if (operationDTO.getOperation().startsWith(QUERY)) {
			strategyName = "queryCubeStrategy";
		}
		if (strategyName == null) {
			ECodeStatus operStatus = ECodeStatus.OPERATION_UNAUTHORIZED;
			throw new CubeException(operStatus.getCode(), operationDTO.getOperation() + "-" + operStatus.getMsg());
		}
		
		this.strategy = (CubeStrategy) appCtx.getBean(strategyName);
		this.strategy.process(operationDTO, mapCube);
	}
}
