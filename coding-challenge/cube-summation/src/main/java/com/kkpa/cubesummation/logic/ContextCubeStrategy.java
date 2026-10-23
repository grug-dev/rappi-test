package com.kkpa.cubesummation.logic;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kkpa.cubesummation.dto.OperationDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.enums.ETypeOperation;
import com.kkpa.cubesummation.exceptions.CubeException;

/**
 * Picks the strategy for each operation. Spring gives all CubeStrategy beans,
 * so a new operation needs no change here. The class has no mutable state and
 * is safe to share between requests.
 */
@Component
public class ContextCubeStrategy {

	private final Map<ETypeOperation, CubeStrategy> strategies;

	public ContextCubeStrategy(List<CubeStrategy> strategyList) {
		Map<ETypeOperation, CubeStrategy> byType = new EnumMap<ETypeOperation, CubeStrategy>(ETypeOperation.class);
		for (CubeStrategy strategy : strategyList) {
			CubeStrategy previous = byType.put(strategy.type(), strategy);
			if (previous != null) {
				throw new IllegalStateException("Two strategies handle the operation " + strategy.type());
			}
		}
		this.strategies = Collections.unmodifiableMap(byType);
	}

	public void process(OperationDTO operationDTO, Map<String, Long> mapCube) throws CubeException {
		String operation = operationDTO.getOperation();
		CubeStrategy strategy = strategies.get(findType(operation));
		if (strategy == null) {
			throw new CubeException(ECodeStatus.OPERATION_UNAUTHORIZED, operation);
		}
		strategy.process(operationDTO, mapCube);
	}

	/**
	 * Reads the operation name, the first word of the line.
	 * Returns null when the name is not a known operation.
	 */
	private static ETypeOperation findType(String operation) {
		if (operation == null || operation.trim().isEmpty()) {
			return null;
		}
		String name = CubeStrategy.split(operation)[0];
		for (ETypeOperation type : ETypeOperation.values()) {
			if (type.name().equals(name)) {
				return type;
			}
		}
		return null;
	}
}
