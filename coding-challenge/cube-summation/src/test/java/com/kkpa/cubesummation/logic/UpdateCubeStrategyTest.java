package com.kkpa.cubesummation.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.kkpa.cubesummation.dto.OperationDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.enums.ETypeOperation;
import com.kkpa.cubesummation.exceptions.CubeException;

public class UpdateCubeStrategyTest {

	private UpdateCubeStrategy strategy;

	private Map<String, Long> mapCube;

	@Before
	public void setUp() {
		strategy = new UpdateCubeStrategy();
		mapCube = new HashMap<String, Long>();
	}

	@Test
	public void handlesUpdateType() {
		assertEquals(ETypeOperation.UPDATE, strategy.type());
	}

	@Test
	public void updateStoresValueInBlock() throws CubeException {
		OperationDTO operation = operation("UPDATE 2 2 2 4");

		strategy.process(operation, mapCube);

		assertEquals(Long.valueOf(4), mapCube.get("2-2-2"));
		assertEquals(CubeStrategy.OK, operation.getResult());
		assertEquals(ETypeOperation.UPDATE, operation.getType());
	}

	@Test
	public void updateReplacesOldValue() throws CubeException {
		strategy.process(operation("UPDATE 1 1 1 5"), mapCube);
		strategy.process(operation("UPDATE 1 1 1 7"), mapCube);

		assertEquals(1, mapCube.size());
		assertEquals(Long.valueOf(7), mapCube.get("1-1-1"));
	}

	@Test
	public void updateUsesNumbersForKeyAndIgnoresExtraSpaces() throws CubeException {
		strategy.process(operation("  UPDATE  02 2   2 4 "), mapCube);

		assertEquals(Long.valueOf(4), mapCube.get("2-2-2"));
	}

	@Test
	public void updateWithWrongLengthFails() {
		assertFails("UPDATE 2 2 3", ECodeStatus.UPDATE_RULE_LENGTH_ERROR);
	}

	@Test
	public void updateWithWrongNameFails() {
		assertFails("QUERY 2 2 2 3", ECodeStatus.UPDATE_OPERATION);
	}

	@Test
	public void updateWithTextValueFails() {
		assertFails("UPDATE 2 2 2 abc", ECodeStatus.NUMBER_ERROR);
	}

	@Test
	public void updateWithTextCoordinateFails() {
		assertFails("UPDATE x 2 2 4", ECodeStatus.NUMBER_ERROR);
	}

	@Test
	public void updateWithCoordinateBelowOneFails() {
		assertFails("UPDATE 0 2 2 4", ECodeStatus.COORDINATE_ERROR);
	}

	private void assertFails(String line, ECodeStatus expected) {
		try {
			strategy.process(operation(line), mapCube);
			fail("Expected CubeException " + expected);
		} catch (CubeException e) {
			assertEquals(expected, e.getStatus());
		}
	}

	private static OperationDTO operation(String line) {
		OperationDTO operation = new OperationDTO();
		operation.setOperation(line);
		return operation;
	}

}
