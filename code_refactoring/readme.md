# Malas Prácticas

*	El ID del conductor podría no existir, y no se realizó validación.
*	No se verificó el que el conductor estuviera disponible.
*	Se realizan 2 validaciones de estado con if, para conocer si el servicio está disponible.
*	Se realiza 2 transacciones de update del servicio, y se podría resumir en una.
*	Los estados se manejan con números, y no es fácil de interpretar.
*	Es confuso que el código error 0, sea catalogado como una respuesta exitosa. 
*	El servicio de notificaciones no se tiene centralizado.
*	Es confuso el manejo del uuid del user. Asumo que la solicitud no se realizó por aplicación móvil y no es necesario enviar una notificación.
*	Se podrían reducir los if-else para que sea más fácil leer el código.
*	Numerosos parámetros enviados al método de notificaciones.


# Refactorización

El paquete **service** es la capa intermedia entre el controller y la capa de persistencia.
La capa de persistencia no se implementó.


*	Se unifica las validaciones de disponibilidad del servicio .
```java
if (!assistanceService.isAvailable(serviceDTO)) {
	return EResponseStatus.NOT_AVAILABLE.getCode();
}
```
*	Se añade validación conductor antes de iniciar con la lógica de negocio.
```java
driverDTO = driverService.find(idDriver);
if (driverDTO == null) {
	return EDriverStatus.NOT_EXIST.getCode();
}

if (!driverService.isAvailable(driverDTO)) {
	return EDriverStatus.NOT_AVAILABLE.getCode();
}
```
*	Se realiza una única transacción update del servicio.
```java
serviceDTO.setStatus(EServiceStatus.ASSIGNED);
serviceDTO.setIdDriver(driverDTO.getIdDriver());
serviceDTO.setIdCar(driverDTO.getIdCar());
assistanceService.update(serviceDTO);
```
*	Se crea una factoría de notificaciones, la cuál dependiendo del código del tipo, decidirá que tipo de notificación se enviará.
```java
String message = "Tu servicio ha sido confirmado!";
NotificationFactory factory = new NotificationFactory();
NotificationSender sender = factory.getSender(serviceDTO.getUser().getType());
try {
	sender.send(serviceDTO, message);
} catch (Exception e) {
	result = EResponseStatus.NOTIFICATION_ERROR.getCode();
}
```
*	Se crean enumerados para manejar los códigos de los estados.
*	Se reduce el uso de los if-else.

## Código Completo

```java
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
```