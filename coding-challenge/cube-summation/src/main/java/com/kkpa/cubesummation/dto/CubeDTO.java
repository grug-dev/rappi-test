package com.kkpa.cubesummation.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kkpa.cubesummation.enums.ECodeStatus;

@Component("cubeDTO")
@Scope("prototype")
public class CubeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7561487738206574760L;
	
	private int status = ECodeStatus.OK.getCode();

	private int matrixN;
	
	private List<OperationDTO> operations = new ArrayList<OperationDTO>();
	
	private List<Long> lstResults = new ArrayList<Long>();
	
	
	@JsonIgnore
	private Map<String,Long> mapCube = new HashMap<String,Long>();
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getMatrixN() {
		return matrixN;
	}

	public void setMatrixN(int matrixN) {
		this.matrixN = matrixN;
	}

	public List<OperationDTO> getOperations() {
		return operations;
	}

	public void setOperations(List<OperationDTO> operations) {
		this.operations = operations;
	}

	public List<Long> getResult() {
		return lstResults;
	}

	public void setResult(List<Long> result) {
		this.lstResults = result;
	}
	
	public Map<String, Long> getMapCube() {
		return mapCube;
	}

	public void setMapCube(Map<String, Long> mapCube) {
		this.mapCube = mapCube;
	}


	@Override
	public String toString() {
		return "CubeDTO [N=" + matrixN + ", operations=" + operations + "]";
	}
	
	
	
}
