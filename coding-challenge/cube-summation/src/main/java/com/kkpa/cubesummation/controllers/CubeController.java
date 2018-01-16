package com.kkpa.cubesummation.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kkpa.cubesummation.dto.CubeDTO;
import com.kkpa.cubesummation.dto.CubeTestCasesDTO;
import com.kkpa.cubesummation.dto.OperationDTO;
import com.kkpa.cubesummation.enums.ECodeStatus;
import com.kkpa.cubesummation.exceptions.CubeException;
import com.kkpa.cubesummation.service.CubeService;

@RestController
@RequestMapping("/cube")
public class CubeController {
	
	@Autowired
	private ApplicationContext appCtx;
	
	@Autowired
	@Qualifier("cubeSrv")
	private CubeService cubeService; 
	
	
	@PostMapping("/upload")
    public ResponseEntity<CubeTestCasesDTO> uploadFile(@RequestParam("file") MultipartFile uploadfile) {
		 CubeTestCasesDTO cubeTestCases =  getCubeTestCases();

        if (uploadfile.isEmpty()) {
        	cubeTestCases.setStatus(ECodeStatus.ERROR.getCode());
        	cubeTestCases.setMsg("El archivo está vacío");
            return new ResponseEntity<CubeTestCasesDTO>(cubeTestCases, HttpStatus.OK);
        }

        try {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(uploadfile.getInputStream()));
            String line = null;            
            String lineNxM = null;
            int n = 0, m = 0;
            CubeDTO cubeDTO = getCubeDTO();
            OperationDTO operDTO = getOperationDTO();
            reader.readLine();
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                 if (lineNxM == null) {
                	 lineNxM = line;
                	 StringTokenizer token = new StringTokenizer(lineNxM);
                	 String nLine = token.nextElement().toString();
                	 try {                		 
                		 n = Integer.parseInt(nLine);
                	 }catch (Exception e) {      
                		 ECodeStatus error = ECodeStatus.MATRIX_ERROR;     
                		 error.setMsg(error.getMsg() + " - " + nLine);
                		 throw new CubeException(error);
                	 }
                	 String mLine = token.nextElement().toString();
                	 try {
                		 m = Integer.parseInt(mLine);
                	 } catch (Exception e) {
                		 ECodeStatus error = ECodeStatus.OPERATION_ERROR;     
                		 error.setMsg(error.getMsg() + " - " + mLine);
                		 throw new CubeException(error);
                	 }
                	
                	 continue;
                 }
                 operDTO = getOperationDTO();
                 operDTO.setOperation(line);
                 cubeDTO.getOperations().add(operDTO);
                 if (m == cubeDTO.getOperations().size()) {
                	 cubeDTO.setMatrixN(n);
                	 cubeTestCases.getLstCases().add(cubeDTO);
                	 cubeDTO = getCubeDTO();
                	 lineNxM = null;                	 
                 }                                 
            }
            reader.close();
            
            
            // Process Test Cases
            cubeService.processTestCases(cubeTestCases);
            

        }  catch (Exception e) {
        	e.printStackTrace();        	
        	if (e instanceof CubeException) {
        		cubeTestCases.setStatus(((CubeException) e).getCode());
        		cubeTestCases.setMsg(((CubeException) e).getMsg());
        	}else {
        		cubeTestCases.setMsg(ECodeStatus.ERROR.getMsg());
        		cubeTestCases.setStatus(ECodeStatus.ERROR.getCode());
        	}
        	
        	new ResponseEntity<CubeTestCasesDTO>( cubeTestCases, new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<CubeTestCasesDTO>( cubeTestCases, new HttpHeaders(), HttpStatus.OK);

    }
	
	private OperationDTO getOperationDTO() {
		return (OperationDTO) appCtx.getBean("operationDTO");
	}
	
	private CubeDTO getCubeDTO() {
		return (CubeDTO) appCtx.getBean("cubeDTO");
	}
	
	private CubeTestCasesDTO getCubeTestCases() {
		return (CubeTestCasesDTO) appCtx.getBean("cubeTestCases");
	}

}
