package com.kkpa.coderefactoring.notifications;

import com.kkpa.coderefactoring.enums.ETypeSender;

public class NotificationFactory {

	
	public NotificationSender getSender(int type) {
		
		if (type == ETypeSender.IPHONE.getCode()) {
			return new IphoneNotificationSender();
		}
		
		if (type == ETypeSender.ANDROID.getCode()) {
			return new AndroidNotificationSender();
		}
		
		return null;
	}
	
}
