package com.kkpa.cubesummation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkpa.cubesummation.dto.CubeDTO;
import com.kkpa.cubesummation.dto.CubeTestCases;
import com.kkpa.cubesummation.dto.OperationDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.enums.ETypeOperation;
import com.kkpa.cubesummation.exceptions.CubeException;
import com.kkpa.cubesummation.service.CubeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CubeSummationApplicationTests {

	@Autowired
	private ApplicationContext appCtx;

	private CubeDTO cubeDTO;

	private CubeService cubeService;

	@Before
	public void setUp() {
		cubeDTO = (CubeDTO) appCtx.getBean("cubeDTO");
		cubeService = (CubeService) appCtx.getBean("cubeSrv");
	}

	@After
	public void tearDown() {
		cubeDTO = null;
	}

	/**
	 * Test following Operation: 4 1 UPDATING 2 2 3
	 */
	@Test
	public void testFailUpdateOperation() {
		final String operation = "UPDATING 2 2 3";

		OperationDTO operationDTO = new OperationDTO();
		operationDTO.setType(ETypeOperation.UPDATE);
		operationDTO.setOperation(operation);
		cubeDTO.getOperations().add(operationDTO);
		cubeDTO.setMatrixN(4);
		try {
			cubeService.processCube(cubeDTO);
		} catch (CubeException e) {
			assertEquals(ECodeStatus.OPERATION_UNAUTHORIZED.getCode(), e.getCode());
		}
	}

	/**
	 * Test following Operation: 4 1 CONSULTA 1 1 1 3 3 3
	 */
	@Test
	public void testFailQueryOperation() {
		final String operation = "CONSULTA 1 1 1 3 3 3";

		OperationDTO operationDTO = new OperationDTO();
		operationDTO.setType(ETypeOperation.QUERY);
		operationDTO.setOperation(operation);
		cubeDTO.getOperations().add(operationDTO);
		cubeDTO.setMatrixN(4);
		try {
			cubeService.processCube(cubeDTO);
		} catch (CubeException e) {
			assertEquals(ECodeStatus.OPERATION_UNAUTHORIZED.getCode(), e.getCode());
		}
	}

	/**
	 * Test Operation: 4 1 UPDATE 2 2 2 4
	 */
	@Test
	public void testSucessSingleUpdateOperation() {
		final String operation = "UPDATE 2 2 2 4";

		OperationDTO operationDTO = new OperationDTO();
		operationDTO.setType(ETypeOperation.UPDATE);
		operationDTO.setOperation(operation);
		try {
			cubeDTO.getOperations().add(operationDTO);
			cubeDTO.setMatrixN(4);
			cubeService.processCube(cubeDTO);
			// Assert Status & Result
			assertEquals(200, operationDTO.getStatus());
			assertEquals("OK", operationDTO.getResult());

		} catch (CubeException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test Operation: 4 1 UPDATE 2 2 3
	 */
	@Test
	public void testSingleFailUpdateOperation() {
		final String operation = "UPDATE 2 2 3";

		OperationDTO operationDTO = new OperationDTO();
		operationDTO.setType(ETypeOperation.UPDATE);
		operationDTO.setOperation(operation);
		cubeDTO.getOperations().add(operationDTO);
		cubeDTO.setMatrixN(4);
		try {
			cubeService.processCube(cubeDTO);
		} catch (CubeException e) {
			assertEquals(ECodeStatus.UPDATE_RULE_LENGTH_ERROR.getCode(), e.getCode());
		}
	}

	/**
	 * Test Next Operation: 2 QUERY 1 1
	 */
	@Test
	public void testSingleFailQueryOperation() {
		final String operation = "QUERY 1 1";

		OperationDTO operationDTO = new OperationDTO();
		operationDTO.setType(ETypeOperation.QUERY);
		operationDTO.setOperation(operation);
		cubeDTO.getOperations().add(operationDTO);
		cubeDTO.setMatrixN(4);
		try {
			cubeService.processCube(cubeDTO);
		} catch (CubeException e) {
			assertEquals(ECodeStatus.QUERY_RULE_LENGTH_ERROR.getCode(), e.getCode());
		}

	}

	/**
	 * Test Next Operation: 4 1 QUERY 1 1 1 3 3 3
	 */
	@Test
	public void testSucessSingleQueryOperation() {
		final String operation = "QUERY 1 1 1 3 3 3";

		OperationDTO operationDTO = new OperationDTO();
		operationDTO.setType(ETypeOperation.QUERY);
		operationDTO.setOperation(operation);
		try {
			cubeDTO.getOperations().add(operationDTO);
			cubeDTO.setMatrixN(4);
			cubeService.processCube(cubeDTO);
			// Assert Status & Result
			assertEquals(200, operationDTO.getStatus());
			assertEquals("0", operationDTO.getResult());

		} catch (CubeException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test: <br>
	 * 1 <br>
	 * 4 5 <br>
	 * UPDATE 2 2 2 4 <br>
	 * QUERY 1 1 1 3 3 3 <br>
	 * UPDATE 1 1 1 23 <br>
	 * QUERY 2 2 2 4 4 4 <br>
	 * QUERY 1 1 1 3 3 3 <br>
	 */
	@Test
	public void testSuccessSingleTestCase() {
		String operation = null;
		OperationDTO operationDTO = null;
		try {
			cubeDTO.setMatrixN(4);
			// UPDATE 2 2 2 4
			operation = "UPDATE 2 2 2 4";
			operationDTO = (OperationDTO) appCtx.getBean("operationDTO");
			operationDTO.setType(ETypeOperation.UPDATE);
			operationDTO.setOperation(operation);
			cubeDTO.getOperations().add(operationDTO);

			// QUERY 1 1 1 3 3 3
			operation = "QUERY 1 1 1 3 3 3";
			operationDTO = (OperationDTO) appCtx.getBean("operationDTO");
			operationDTO.setType(ETypeOperation.QUERY);
			operationDTO.setOperation(operation);
			cubeDTO.getOperations().add(operationDTO);

			// UPDATE 1 1 1 23
			operation = "UPDATE 1 1 1 23";
			operationDTO = (OperationDTO) appCtx.getBean("operationDTO");
			operationDTO.setType(ETypeOperation.UPDATE);
			operationDTO.setOperation(operation);
			cubeDTO.getOperations().add(operationDTO);

			// QUERY 2 2 2 4 4 4
			operation = "QUERY 2 2 2 4 4 4";
			operationDTO = (OperationDTO) appCtx.getBean("operationDTO");
			operationDTO.setType(ETypeOperation.QUERY);
			operationDTO.setOperation(operation);
			cubeDTO.getOperations().add(operationDTO);

			// QUERY 1 1 1 3 3 3
			operation = "QUERY 1 1 1 3 3 3";
			operationDTO = (OperationDTO) appCtx.getBean("operationDTO");
			operationDTO.setType(ETypeOperation.QUERY);
			operationDTO.setOperation(operation);
			cubeDTO.getOperations().add(operationDTO);

			cubeService.processCube(cubeDTO);
			// Assert Status
			assertEquals(200, cubeDTO.getStatus());
			// 3 Queries
			assertThat(cubeDTO.getResult(), hasSize(3));
			// 3 Results
			assertThat(cubeDTO.getResult(), contains(4l, 4l, 27l));

		} catch (CubeException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testMultipleSuccessTestCases() {
		CubeTestCases testCases = (CubeTestCases) appCtx.getBean("cubeTestCases");
		String operation = null;
		OperationDTO operationDTO = null;
		// 1 Case
		// 4 5
		// UPDATE 2 2 2 1
		// QUERY 1 1 1 1 1 1
		// QUERY 1 1 1 2 2 2
		// QUERY 2 2 2 2 2 2
		cubeDTO.setMatrixN(4);
		// UPDATE 2 2 2 4
		operation = "UPDATE 2 2 2 4";
		operationDTO = (OperationDTO) appCtx.getBean("operationDTO");
		operationDTO.setType(ETypeOperation.UPDATE);
		operationDTO.setOperation(operation);
		cubeDTO.getOperations().add(operationDTO);

		// QUERY 1 1 1 3 3 3
		operation = "QUERY 1 1 1 3 3 3";
		operationDTO = (OperationDTO) appCtx.getBean("operationDTO");
		operationDTO.setType(ETypeOperation.QUERY);
		operationDTO.setOperation(operation);
		cubeDTO.getOperations().add(operationDTO);

		// UPDATE 1 1 1 23
		operation = "UPDATE 1 1 1 23";
		operationDTO = (OperationDTO) appCtx.getBean("operationDTO");
		operationDTO.setType(ETypeOperation.UPDATE);
		operationDTO.setOperation(operation);
		cubeDTO.getOperations().add(operationDTO);

		// QUERY 2 2 2 4 4 4
		operation = "QUERY 2 2 2 4 4 4";
		operationDTO = (OperationDTO) appCtx.getBean("operationDTO");
		operationDTO.setType(ETypeOperation.QUERY);
		operationDTO.setOperation(operation);
		cubeDTO.getOperations().add(operationDTO);

		// QUERY 1 1 1 3 3 3
		operation = "QUERY 1 1 1 3 3 3";
		operationDTO = (OperationDTO) appCtx.getBean("operationDTO");
		operationDTO.setType(ETypeOperation.QUERY);
		operationDTO.setOperation(operation);
		cubeDTO.getOperations().add(operationDTO);

		// ****** 2 Case ********
		// 2 4
		// UPDATE 2 2 2 1
		// QUERY 1 1 1 1 1 1
		// QUERY 1 1 1 2 2 2
		// QUERY 2 2 2 2 2 2
		CubeDTO secondCubeDTO = (CubeDTO) appCtx.getBean("cubeDTO");
		secondCubeDTO.setMatrixN(4);
		// UPDATE 2 2 2 1
		operation = "UPDATE 2 2 2 1";
		operationDTO = (OperationDTO) appCtx.getBean("operationDTO");
		operationDTO.setType(ETypeOperation.UPDATE);
		operationDTO.setOperation(operation);
		secondCubeDTO.getOperations().add(operationDTO);

		// QUERY 1 1 1 1 1 1
		operation = "QUERY 1 1 1 1 1 1";
		operationDTO = (OperationDTO) appCtx.getBean("operationDTO");
		operationDTO.setType(ETypeOperation.QUERY);
		operationDTO.setOperation(operation);
		secondCubeDTO.getOperations().add(operationDTO);

		// QUERY 1 1 1 2 2 2
		operation = "QUERY 1 1 1 2 2 2";
		operationDTO = (OperationDTO) appCtx.getBean("operationDTO");
		operationDTO.setType(ETypeOperation.QUERY);
		operationDTO.setOperation(operation);
		secondCubeDTO.getOperations().add(operationDTO);

		// QUERY 2 2 2 2 2 2
		operation = "QUERY 2 2 2 2 2 2";
		operationDTO = (OperationDTO) appCtx.getBean("operationDTO");
		operationDTO.setType(ETypeOperation.QUERY);
		operationDTO.setOperation(operation);
		secondCubeDTO.getOperations().add(operationDTO);

		testCases.getLstCases().add(cubeDTO);
		testCases.getLstCases().add(secondCubeDTO);
		cubeService.processTestCases(testCases);
		
		
		assertThat(testCases.getStatus(),is(200));
		assertThat(testCases.getLstResults(),hasSize(6));
		assertThat(testCases.getLstResults(), contains(4l,4l,27l,0l,1l,1l));
		
	}

}
