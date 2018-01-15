package com.kkpa.coderefactoring.main;

import com.kkpa.coderefactoring.controller.AssistanceController;

public class UserRequest {

	public static void main(String[] args) {
		AssistanceController assistance = new AssistanceController();
		assistance.requestService(1l, 1l);
	}

}
