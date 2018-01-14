package com.kkpa.cubesummation.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("cubeTestCases")
@Scope("prototype")
public class CubeTestCases {

	private List<CubeDTO> lstCases = new ArrayList<CubeDTO>();

	private List<Long> lstResults = new ArrayList<Long>();
	
	private int status;
	
	private String msg;
	
	public List<CubeDTO> getLstCases() {
		return lstCases;
	}

	public void setLstCases(List<CubeDTO> lstCases) {
		this.lstCases = lstCases;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Long> getLstResults() {
		return lstResults;
	}

	public void setLstResults(List<Long> lstResults) {
		this.lstResults = lstResults;
	}
	
}
