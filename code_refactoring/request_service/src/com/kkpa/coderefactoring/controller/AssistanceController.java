package com.kkpa.coderefactoring.controller;

import com.kkpa.coderefactoring.dto.DriverDTO;
import com.kkpa.coderefactoring.dto.ServiceDTO;
import com.kkpa.coderefactoring.enums.EDriverStatus;
import com.kkpa.coderefactoring.enums.EResponseStatus;
import com.kkpa.coderefactoring.enums.EServiceStatus;
import com.kkpa.coderefactoring.notifications.NotificationFactory;
import com.kkpa.coderefactoring.notifications.NotificationSender;
import com.kkpa.coderefactoring.service.AssistanceService;
import com.kkpa.coderefactoring.service.DriverService;

public class AssistanceController {

	public EResponseStatus requestService(long idService, long idDriver) {
		EResponseStatus result = EResponseStatus.OK;
		AssistanceService assistanceService = null; // Injected
		DriverService driverService = null; // Injected
		ServiceDTO serviceDTO = null;
		DriverDTO driverDTO = null;

		// Validando Servicio
		serviceDTO = assistanceService.find(idService);
		if (serviceDTO == null) {
			return EResponseStatus.NOT_EXIST;
		}

		// Validando disponibilidad servicio
		if (!assistanceService.isAvailable(serviceDTO)) {
			return EResponseStatus.NOT_AVAILABLE;
		}

		// Validando Conductor
		driverDTO = driverService.find(idDriver);
		if (driverDTO == null) {
			return EResponseStatus.NOT_EXIST;
		}

		// Validando disponibilidad conductor
		if (!driverService.isAvailable(driverDTO)) {
			return EResponseStatus.NOT_AVAILABLE;
		}

		// Actualizando Servicio.
		serviceDTO.setStatus(EServiceStatus.ASSIGNED);
		serviceDTO.setIdDriver(driverDTO.getIdDriver());
		serviceDTO.setIdCar(driverDTO.getIdCar());
		assistanceService.update(serviceDTO);

		// Actualizando Driver
		driverDTO.setStatus(EDriverStatus.ASSIGNED);
		driverService.update(driverDTO);

		// Enviando Notificacion
		String message = "Tu servicio ha sido confirmado!";
		NotificationFactory factory = new NotificationFactory();
		NotificationSender sender = factory.getSender(serviceDTO.getUser().getType());
		try {
			sender.send(serviceDTO, message);
		} catch (Exception e) {
			result = EResponseStatus.NOTIFICATION_ERROR;
		}

		return result;
	}

}
