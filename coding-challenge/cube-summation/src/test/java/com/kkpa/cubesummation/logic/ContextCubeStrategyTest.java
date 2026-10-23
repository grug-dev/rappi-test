package com.kkpa.cubesummation.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.kkpa.cubesummation.dto.OperationDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.enums.ETypeOperation;
import com.kkpa.cubesummation.exceptions.CubeException;

public class ContextCubeStrategyTest {

	private ContextCubeStrategy context;

	private Map<String, Long> mapCube;

	@Before
	public void setUp() {
		context = new ContextCubeStrategy(Arrays.<CubeStrategy>asList(new UpdateCubeStrategy(), new QueryCubeStrategy()));
		mapCube = new HashMap<String, Long>();
	}

	@Test
	public void updateGoesToUpdateStrategy() throws CubeException {
		OperationDTO operation = operation("UPDATE 2 2 2 4");

		context.process(operation, mapCube);

		assertEquals(ETypeOperation.UPDATE, operation.getType());
		assertEquals(Long.valueOf(4), mapCube.get("2-2-2"));
	}

	@Test
	public void queryGoesToQueryStrategy() throws CubeException {
		mapCube.put("2-2-2", 4L);
		OperationDTO operation = operation("QUERY 1 1 1 3 3 3");

		context.process(operation, mapCube);

		assertEquals(ETypeOperation.QUERY, operation.getType());
		assertEquals("4", operation.getResult());
	}

	@Test
	public void unknownOperationFailsWithDetail() {
		try {
			context.process(operation("UPDATING 2 2 3"), mapCube);
			fail("Expected CubeException");
		} catch (CubeException e) {
			assertEquals(ECodeStatus.OPERATION_UNAUTHORIZED, e.getStatus());
			assertTrue(e.getMessage().contains("UPDATING 2 2 3"));
		}
	}

	@Test
	public void lowerCaseOperationIsNotAllowed() {
		assertUnauthorized("update 2 2 2 4");
	}

	@Test
	public void blankOperationIsNotAllowed() {
		assertUnauthorized("   ");
	}

	@Test
	public void nullOperationIsNotAllowed() {
		assertUnauthorized(null);
	}

	@Test
	public void sameErrorMessageForEveryRequest() {
		String first = unauthorizedMessage("CONSULTA 1 1 1 3 3 3");
		String second = unauthorizedMessage("CONSULTA 1 1 1 3 3 3");

		// The shared enum message must not grow between requests.
		assertEquals(first, second);
		assertEquals("Operacion no permitida", ECodeStatus.OPERATION_UNAUTHORIZED.getMsg());
	}

	@Test(expected = IllegalStateException.class)
	public void twoStrategiesForSameOperationAreRejected() {
		new ContextCubeStrategy(Arrays.<CubeStrategy>asList(new UpdateCubeStrategy(), new UpdateCubeStrategy()));
	}

	private void assertUnauthorized(String line) {
		try {
			context.process(operation(line), mapCube);
			fail("Expected CubeException");
		} catch (CubeException e) {
			assertEquals(ECodeStatus.OPERATION_UNAUTHORIZED, e.getStatus());
		}
	}

	private String unauthorizedMessage(String line) {
		try {
			context.process(operation(line), mapCube);
			fail("Expected CubeException");
			return null;
		} catch (CubeException e) {
			return e.getMessage();
		}
	}

	private static OperationDTO operation(String line) {
		OperationDTO operation = new OperationDTO();
		operation.setOperation(line);
		return operation;
	}

}
