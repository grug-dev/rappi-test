package com.kkpa.cubesummation.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.kkpa.cubesummation.dto.CubeDTO;
import com.kkpa.cubesummation.dto.CubeTestCasesDTO;
import com.kkpa.cubesummation.dto.OperationDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.exceptions.CubeException;
import com.kkpa.cubesummation.logic.ContextCubeStrategy;
import com.kkpa.cubesummation.logic.CubeStrategy;
import com.kkpa.cubesummation.logic.QueryCubeStrategy;
import com.kkpa.cubesummation.logic.UpdateCubeStrategy;
import com.kkpa.cubesummation.parser.CubeFileParser;

public class CubeServiceImplTest {

	private CubeServiceImpl service;

	@Before
	public void setUp() {
		ContextCubeStrategy context = new ContextCubeStrategy(
				Arrays.<CubeStrategy>asList(new UpdateCubeStrategy(), new QueryCubeStrategy()));
		service = new CubeServiceImpl(context);
	}

	/**
	 * Sample input and output from the HackerRank problem (see coding-challenge/readme.md).
	 */
	@Test
	public void sampleInputGivesSampleOutput() throws Exception {
		CubeTestCasesDTO testCases;
		try (InputStream sample = getClass().getResourceAsStream("/sample-input.txt")) {
			testCases = new CubeFileParser().parse(sample);
		}

		service.processTestCases(testCases);

		assertEquals(ECodeStatus.OK.getCode(), testCases.getStatus());
		assertEquals(ECodeStatus.OK.getMsg(), testCases.getMsg());
		assertThat(testCases.getLstResults(), contains(4L, 4L, 27L, 0L, 1L, 1L));
		assertThat(testCases.getLstCases().get(0).getResult(), contains(4L, 4L, 27L));
		assertThat(testCases.getLstCases().get(1).getResult(), contains(0L, 1L, 1L));
	}

	@Test
	public void processCubeKeepsOnlyQueryResults() throws CubeException {
		CubeDTO cube = cube(4, "UPDATE 2 2 2 4", "QUERY 1 1 1 3 3 3", "UPDATE 1 1 1 23", "QUERY 1 1 1 3 3 3");

		service.processCube(cube);

		assertThat(cube.getResult(), contains(4L, 27L));
		assertEquals("OK", cube.getOperations().get(0).getResult());
	}

	@Test
	public void testCasesDoNotShareCubeValues() throws CubeException {
		CubeTestCasesDTO testCases = new CubeTestCasesDTO();
		testCases.getLstCases().add(cube(2, "UPDATE 1 1 1 9"));
		testCases.getLstCases().add(cube(2, "QUERY 1 1 1 2 2 2"));

		service.processTestCases(testCases);

		assertThat(testCases.getLstResults(), contains(0L));
	}

	@Test
	public void nullTestCasesFails() {
		assertFails(null, ECodeStatus.TEST_CASES_ERROR);
	}

	@Test
	public void emptyTestCasesFails() {
		assertFails(new CubeTestCasesDTO(), ECodeStatus.TEST_CASES_ERROR);
	}

	@Test
	public void operationErrorStopsWithCubeException() {
		CubeTestCasesDTO testCases = new CubeTestCasesDTO();
		testCases.getLstCases().add(cube(4, "UPDATE 2 2 2 4", "QUERY 1 1"));

		assertFails(testCases, ECodeStatus.QUERY_RULE_LENGTH_ERROR);
		assertThat(testCases.getLstResults(), empty());
	}

	private void assertFails(CubeTestCasesDTO testCases, ECodeStatus expected) {
		try {
			service.processTestCases(testCases);
			fail("Expected CubeException " + expected);
		} catch (CubeException e) {
			assertEquals(expected, e.getStatus());
		}
	}

	private static CubeDTO cube(int size, String... lines) {
		CubeDTO cube = new CubeDTO();
		cube.setMatrixN(size);
		for (String line : lines) {
			OperationDTO operation = new OperationDTO();
			operation.setOperation(line);
			cube.getOperations().add(operation);
		}
		return cube;
	}

}
