package com.kkpa.cubesummation.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;

import com.kkpa.cubesummation.dto.CubeDTO;
import com.kkpa.cubesummation.dto.CubeTestCasesDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.exceptions.CubeException;

public class CubeFileParserTest {

	private CubeFileParser parser;

	@Before
	public void setUp() {
		parser = new CubeFileParser();
	}

	@Test
	public void parsesSampleInput() throws Exception {
		CubeTestCasesDTO testCases;
		try (InputStream sample = getClass().getResourceAsStream("/sample-input.txt")) {
			testCases = parser.parse(sample);
		}

		assertEquals(2, testCases.getLstCases().size());

		CubeDTO first = testCases.getLstCases().get(0);
		assertEquals(4, first.getMatrixN());
		assertEquals(5, first.getOperations().size());
		assertEquals("UPDATE 2 2 2 4", first.getOperations().get(0).getOperation());
		assertEquals("QUERY 1 1 1 3 3 3", first.getOperations().get(4).getOperation());

		CubeDTO second = testCases.getLstCases().get(1);
		assertEquals(2, second.getMatrixN());
		assertEquals(4, second.getOperations().size());
		assertEquals("QUERY 2 2 2 2 2 2", second.getOperations().get(3).getOperation());
	}

	@Test
	public void ignoresBlankLinesAndWindowsLineEnds() throws Exception {
		CubeTestCasesDTO testCases = parse("1\r\n\r\n2 2\r\n  UPDATE 1 1 1 5  \r\n\r\nQUERY 1 1 1 2 2 2\r\n\r\n");

		assertEquals(1, testCases.getLstCases().size());
		CubeDTO cube = testCases.getLstCases().get(0);
		assertEquals(2, cube.getOperations().size());
		assertEquals("UPDATE 1 1 1 5", cube.getOperations().get(0).getOperation());
	}

	@Test
	public void ignoresByteOrderMark() throws Exception {
		CubeTestCasesDTO testCases = parse("﻿1\n2 1\nQUERY 1 1 1 2 2 2\n");

		assertEquals(1, testCases.getLstCases().size());
	}

	@Test
	public void emptyFileFails() {
		assertFails("", ECodeStatus.EMPTY_FILE);
		assertFails(" \n \n", ECodeStatus.EMPTY_FILE);
	}

	@Test
	public void textTestCasesCountFails() {
		assertFails("two\n4 1\nQUERY 1 1 1 1 1 1\n", ECodeStatus.TEST_CASES_ERROR);
	}

	@Test
	public void zeroTestCasesFails() {
		assertFails("0\n", ECodeStatus.TEST_CASES_ERROR);
	}

	@Test
	public void textMatrixSizeFails() {
		assertFails("1\nN 1\nQUERY 1 1 1 1 1 1\n", ECodeStatus.MATRIX_ERROR);
	}

	@Test
	public void missingOperationsCountFails() {
		assertFails("1\n4\nQUERY 1 1 1 1 1 1\n", ECodeStatus.OPERATION_ERROR);
	}

	@Test
	public void textOperationsCountFails() {
		assertFails("1\n4 M\nQUERY 1 1 1 1 1 1\n", ECodeStatus.OPERATION_ERROR);
	}

	@Test
	public void missingOperationLinesFails() {
		assertFails("1\n4 3\nUPDATE 1 1 1 1\nQUERY 1 1 1 1 1 1\n", ECodeStatus.INCOMPLETE_FILE);
	}

	@Test
	public void missingTestCaseFails() {
		assertFails("2\n4 1\nQUERY 1 1 1 1 1 1\n", ECodeStatus.INCOMPLETE_FILE);
	}

	private CubeTestCasesDTO parse(String content) throws CubeException, IOException {
		return parser.parse(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
	}

	private void assertFails(String content, ECodeStatus expected) {
		try {
			parse(content);
			fail("Expected CubeException " + expected);
		} catch (CubeException e) {
			assertEquals(expected, e.getStatus());
		} catch (IOException e) {
			fail("Unexpected IOException: " + e.getMessage());
		}
	}

}
