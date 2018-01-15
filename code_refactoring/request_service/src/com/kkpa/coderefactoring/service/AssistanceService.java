package com.kkpa.coderefactoring.service;

import com.kkpa.coderefactoring.dto.ServiceDTO;

public interface AssistanceService {

	ServiceDTO find(Long idService);
	
	/**
	 * Método que invocará la capa de persistencia.
	 * Verifica si el servicio está disponible.
	 * Se considera disponible si el status del servicio es 1.
	 * @param service Servicio a verificar
	 * @return true si está disponible.
	 */
	boolean isAvailable(ServiceDTO service);
	
	void update(ServiceDTO service); 
	
}
