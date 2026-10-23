package com.kkpa.cubesummation.logic;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.kkpa.cubesummation.dto.OperationDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.enums.ETypeOperation;
import com.kkpa.cubesummation.exceptions.CubeException;

/**
 * Runs "UPDATE x y z W": sets block (x,y,z) to W.
 */
@Component
public class UpdateCubeStrategy implements CubeStrategy {

	@Override
	public ETypeOperation type() {
		return ETypeOperation.UPDATE;
	}

	@Override
	public void process(OperationDTO operDTO, Map<String, Long> mapCube) throws CubeException{
		String[] partOperation = CubeStrategy.split(operDTO.getOperation());
		operDTO.setType(ETypeOperation.UPDATE);

		if (partOperation.length != 5) {
			throw new CubeException(ECodeStatus.UPDATE_RULE_LENGTH_ERROR);
		}

		if (!partOperation[0].equals(ETypeOperation.UPDATE.toString())) {
			throw new CubeException(ECodeStatus.UPDATE_OPERATION);
		}

		int x = CubeStrategy.toCoordinate(partOperation[1]);
		int y = CubeStrategy.toCoordinate(partOperation[2]);
		int z = CubeStrategy.toCoordinate(partOperation[3]);
		long value = CubeStrategy.toNumber(partOperation[4]);
		// Build the key from the parsed numbers, so "02" and "2" are the same block.
		String key = x + TOKEN_KEY_MAP + y + TOKEN_KEY_MAP + z;
		mapCube.put(key, value);
		operDTO.setResult(OK);
	}

}
