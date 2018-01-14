package com.kkpa.cubesummation.logic;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.kkpa.cubesummation.dto.OperationDTO;
import com.kkpa.cubesummation.enums.ETypeOperation;

@Component("updateCubeStrategy")
public class UpdateCubeStrategy implements CubeStrategy {

	@Override
	public void process(OperationDTO operDTO, Map<String, Long> mapCube) {
		String operation = operDTO.getOperation();
		operDTO.setType(ETypeOperation.UPDATE);
		
		String[] partOperation = operation.split(" ");
		long value = Long.parseLong(partOperation[4]);
		String x = partOperation[1];
		String y = partOperation[2];
		String z = partOperation[3];
		String key = String.join(TOKEN_KEY_MAP, x,y,z);
		mapCube.put(key, value);
		operDTO.setResult("OK");
	}

}
