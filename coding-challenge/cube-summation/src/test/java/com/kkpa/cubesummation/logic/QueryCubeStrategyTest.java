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

public class QueryCubeStrategyTest {

	private QueryCubeStrategy strategy;

	private Map<String, Long> mapCube;

	@Before
	public void setUp() {
		strategy = new QueryCubeStrategy();
		mapCube = new HashMap<String, Long>();
		mapCube.put("1-1-1", 23L);
		mapCube.put("2-2-2", 4L);
		mapCube.put("4-4-4", 100L);
	}

	@Test
	public void handlesQueryType() {
		assertEquals(ETypeOperation.QUERY, strategy.type());
	}

	@Test
	public void querySumsBlocksInsideRange() throws CubeException {
		OperationDTO operation = operation("QUERY 1 1 1 3 3 3");

		strategy.process(operation, mapCube);

		assertEquals("27", operation.getResult());
		assertEquals(ETypeOperation.QUERY, operation.getType());
	}

	@Test
	public void queryIncludesRangeLimits() throws CubeException {
		OperationDTO operation = operation("QUERY 2 2 2 4 4 4");

		strategy.process(operation, mapCube);

		assertEquals("104", operation.getResult());
	}

	@Test
	public void queryOnEmptyCubeReturnsZero() throws CubeException {
		OperationDTO operation = operation("QUERY 1 1 1 4 4 4");

		strategy.process(operation, new HashMap<String, Long>());

		assertEquals("0", operation.getResult());
	}

	@Test
	public void queryWithWrongLengthFails() {
		assertFails("QUERY 1 1", ECodeStatus.QUERY_RULE_LENGTH_ERROR);
	}

	@Test
	public void queryWithWrongNameFails() {
		assertFails("UPDATE 1 1 1 3 3 3", ECodeStatus.QUERY_OPERATION);
	}

	@Test
	public void queryWithTextCoordinateFails() {
		assertFails("QUERY 1 1 1 3 3 z", ECodeStatus.NUMBER_ERROR);
	}

	@Test
	public void queryWithCoordinateBelowOneFails() {
		assertFails("QUERY -1 1 1 3 3 3", ECodeStatus.COORDINATE_ERROR);
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
