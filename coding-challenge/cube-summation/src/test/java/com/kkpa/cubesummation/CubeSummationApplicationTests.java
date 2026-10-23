package com.kkpa.cubesummation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.kkpa.cubesummation.enums.ECodeStatus;

/**
 * Starts the Spring context and calls POST /cube/upload end to end.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CubeSummationApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void uploadSampleFileReturnsSampleOutput() throws Exception {
		MockMultipartFile file;
		try (InputStream sample = getClass().getResourceAsStream("/sample-input.txt")) {
			file = new MockMultipartFile("file", "sample-input.txt", "text/plain", sample);
		}

		mockMvc.perform(fileUpload("/cube/upload").file(file))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is(ECodeStatus.OK.getCode())))
				.andExpect(jsonPath("$.lstResults", contains(4, 4, 27, 0, 1, 1)));
	}

	@Test
	public void uploadBadOperationReturnsErrorStatusInBody() throws Exception {
		byte[] content = "1\n4 1\nUPDATING 2 2 3\n".getBytes(StandardCharsets.UTF_8);
		MockMultipartFile file = new MockMultipartFile("file", "bad.txt", "text/plain", content);

		// HTTP 200 on purpose: the front reads the error from the body.
		mockMvc.perform(fileUpload("/cube/upload").file(file))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is(ECodeStatus.OPERATION_UNAUTHORIZED.getCode())))
				.andExpect(jsonPath("$.lstResults", empty()));
	}

	@Test
	public void uploadEmptyFileReturnsEmptyFileStatus() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "empty.txt", "text/plain", new byte[0]);

		mockMvc.perform(fileUpload("/cube/upload").file(file))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is(ECodeStatus.EMPTY_FILE.getCode())));
	}

}
