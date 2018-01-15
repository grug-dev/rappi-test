package com.kkpa.coderefactoring.notifications;

import com.kkpa.coderefactoring.dto.ServiceDTO;

public interface NotificationSender {
	
	
	public void send(ServiceDTO serviceDTO, String message) throws Exception;

}
