package com.kkpa.cubesummation.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import com.kkpa.cubesummation.dto.CubeDTO;
import com.kkpa.cubesummation.dto.CubeTestCasesDTO;
import com.kkpa.cubesummation.dto.OperationDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.exceptions.CubeException;

/**
 * Reads the uploaded text file into test cases. It only reads the format;
 * it does not run the operations.
 *
 * <pre>
 * T            number of test cases
 * N M          for each test case: matrix size and number of operations
 * operation    M lines with UPDATE or QUERY
 * </pre>
 *
 * Blank lines are ignored.
 */
@Component
public class CubeFileParser {

	/** Some editors (for example Notepad) write this mark at the start of the file. */
	private static final String UTF8_BOM = "﻿";

	public CubeTestCasesDTO parse(InputStream input) throws CubeException, IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
			String firstLine = nextLine(reader);
			if (firstLine == null) {
				throw new CubeException(ECodeStatus.EMPTY_FILE);
			}
			if (firstLine.startsWith(UTF8_BOM)) {
				firstLine = firstLine.substring(UTF8_BOM.length()).trim();
			}
			int testCasesCount = toPositiveNumber(firstLine, ECodeStatus.TEST_CASES_ERROR);

			CubeTestCasesDTO testCases = new CubeTestCasesDTO();
			for (int i = 1; i <= testCasesCount; i++) {
				testCases.getLstCases().add(readTestCase(reader, i));
			}
			return testCases;
		}
	}

	private CubeDTO readTestCase(BufferedReader reader, int testCaseNumber) throws CubeException, IOException {
		String sizeLine = nextLine(reader);
		if (sizeLine == null) {
			throw new CubeException(ECodeStatus.INCOMPLETE_FILE, "test case " + testCaseNumber);
		}
		String[] parts = sizeLine.split("\\s+");
		CubeDTO cubeDTO = new CubeDTO();
		cubeDTO.setMatrixN(toPositiveNumber(parts[0], ECodeStatus.MATRIX_ERROR));
		if (parts.length != 2) {
			throw new CubeException(ECodeStatus.OPERATION_ERROR, sizeLine);
		}
		int operationsCount = toPositiveNumber(parts[1], ECodeStatus.OPERATION_ERROR);

		for (int i = 0; i < operationsCount; i++) {
			String line = nextLine(reader);
			if (line == null) {
				throw new CubeException(ECodeStatus.INCOMPLETE_FILE, "test case " + testCaseNumber);
			}
			OperationDTO operationDTO = new OperationDTO();
			operationDTO.setOperation(line);
			cubeDTO.getOperations().add(operationDTO);
		}
		return cubeDTO;
	}

	/**
	 * Returns the next line that is not blank, without spaces at the ends,
	 * or null at the end of the file.
	 */
	private static String nextLine(BufferedReader reader) throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			String trimmed = line.trim();
			if (!trimmed.isEmpty()) {
				return trimmed;
			}
		}
		return null;
	}

	private static int toPositiveNumber(String value, ECodeStatus error) throws CubeException {
		int number;
		try {
			number = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new CubeException(error, value);
		}
		if (number < 1) {
			throw new CubeException(error, value);
		}
		return number;
	}

}
