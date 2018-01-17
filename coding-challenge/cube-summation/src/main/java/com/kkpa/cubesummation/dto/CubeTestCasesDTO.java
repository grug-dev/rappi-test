package com.kkpa.cubesummation.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("cubeTestCases")
@Scope("prototype")
public class CubeTestCasesDTO {

	private int status;

	private String msg;

	private List<CubeDTO> lstCases = new ArrayList<CubeDTO>();

	private List<Long> lstResults = new ArrayList<Long>();

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

	@Override
	public String toString() {
		return "CubeTestCasesDTO [lstTestCases=" + toStringTestCases(lstCases) + "]";
	}
	
	private String toStringTestCases(List<CubeDTO> lstCases) {
		if (lstCases.isEmpty()) {
			return  "0";
		}
		
		return lstCases.stream().map(c -> c.toString()).reduce( (a,b) -> a.toString() + "-" + b.toString()).get();
	}

}
