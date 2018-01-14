package com.kkpa.cubesummation.logic;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.kkpa.cubesummation.dto.OperationDTO;
import com.kkpa.cubesummation.enums.ETypeOperation;

@Component("queryCubeStrategy")
public class QueryCubeStrategy implements CubeStrategy {

	@Override
	public void process(OperationDTO operDTO, Map<String, Long> mapCube) {
		String operation = operDTO.getOperation();
		long sumBlock = 0;
		operDTO.setType(ETypeOperation.QUERY);
		
		String[] partOperation = operation.split(" ");
		int x0 = Integer.parseInt(partOperation[1]);
		int y0 = Integer.parseInt(partOperation[2]);
		int z0 = Integer.parseInt(partOperation[3]);
		int xN = Integer.parseInt(partOperation[4]);
		int yN = Integer.parseInt(partOperation[5]);
		int zN = Integer.parseInt(partOperation[6]);
		
		for (Entry<String, Long> entry : mapCube.entrySet()) {
			String[] key = entry.getKey().split(TOKEN_KEY_MAP);
			int x = Integer.parseInt(key[0]);
			int y = Integer.parseInt(key[1]);
			int z = Integer.parseInt(key[2]);

			if (isBetween(x, x0, xN) && isBetween(y, y0, yN) && isBetween(z, z0, zN)) {
				sumBlock += entry.getValue();
			}
		}
		operDTO.setResult(String.valueOf(sumBlock));

	}
	
	private static boolean isBetween(int value, int start, int end) {
		return (value >= start && value <= end);
	}

}
