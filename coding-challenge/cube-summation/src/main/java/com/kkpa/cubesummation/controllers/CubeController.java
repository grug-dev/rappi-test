package com.kkpa.cubesummation.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kkpa.cubesummation.dto.CubeTestCasesDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.exceptions.CubeException;
import com.kkpa.cubesummation.parser.CubeFileParser;
import com.kkpa.cubesummation.service.CubeService;

/**
 * HTTP entry point. It reads the uploaded file and returns the results.
 * Errors are handled by CubeControllerAdvice.
 */
@RestController
@RequestMapping("/cube")
public class CubeController {

	private final CubeService cubeService;

	private final CubeFileParser cubeFileParser;

	public CubeController(CubeService cubeService, CubeFileParser cubeFileParser) {
		this.cubeService = cubeService;
		this.cubeFileParser = cubeFileParser;
	}

	@PostMapping("/upload")
	public ResponseEntity<CubeTestCasesDTO> uploadFile(@RequestParam("file") MultipartFile uploadfile) throws CubeException, IOException {
		if (uploadfile.isEmpty()) {
			throw new CubeException(ECodeStatus.EMPTY_FILE);
		}

		CubeTestCasesDTO cubeTestCases = cubeFileParser.parse(uploadfile.getInputStream());
		cubeService.processTestCases(cubeTestCases);

		return ResponseEntity.ok(cubeTestCases);
	}

}
