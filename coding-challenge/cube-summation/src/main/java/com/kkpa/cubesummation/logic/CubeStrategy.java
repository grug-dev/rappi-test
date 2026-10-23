package com.kkpa.cubesummation.logic;

import java.util.Map;

import com.kkpa.cubesummation.dto.OperationDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.enums.ETypeOperation;
import com.kkpa.cubesummation.exceptions.CubeException;

/**
 * One cube operation. To add a new operation, add a value to ETypeOperation
 * and a new Spring bean that implements this interface.
 */
public interface CubeStrategy {

	String TOKEN_KEY_MAP = "-";

	String OK = "OK";

	/**
	 * @return the operation this strategy handles.
	 */
	ETypeOperation type();

	void process(OperationDTO operDTO, Map<String, Long> mapCube) throws CubeException;

	/**
	 * Splits an operation line into its parts. Extra spaces are ignored.
	 */
	static String[] split(String operation) {
		return operation.trim().split("\\s+");
	}

	/**
	 * Reads one number of an operation line.
	 */
	static long toNumber(String value) throws CubeException {
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			throw new CubeException(ECodeStatus.NUMBER_ERROR, value);
		}
	}

	/**
	 * Reads one coordinate of an operation line. The first block is (1,1,1),
	 * so a coordinate must be 1 or more.
	 */
	static int toCoordinate(String value) throws CubeException {
		int coordinate;
		try {
			coordinate = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new CubeException(ECodeStatus.NUMBER_ERROR, value);
		}
		if (coordinate < 1) {
			throw new CubeException(ECodeStatus.COORDINATE_ERROR, value);
		}
		return coordinate;
	}

}
