# Preguntas

1.	<strong>¿En qué consiste el principio de responsabilidad única? ¿Cuál es su propósito?</strong>

Es el primer principio SOLID. Consiste en que los métodos definidos para una clase deberían tener un único y común objetivo.
Un objeto de dicha clase sólo debería estar enfocado en ejecutar comportamientos relacionados a un único concepto.
Este concepto es subjetivo ya que depende del negocio y del software que estemos implementando, pero existen conceptos
que fácilmente se pueden detectar su única responsabilidad. Por ejemplo, la acción de generar un archivo.

Considero que el concepto de única responsabilidad depende del criterio de cada desarrollador, pero existen mecanismos que nos 
ayudan a identificar si nuestra clase cumple con este princpio:

*	Cantidad de métodos públicos expuestos.
*	Cantidad de imports en la clase.
*	Dependencia con otra capa de la arquitectura. 
*	Cantidad de LDC.

2.	<strong>¿Qué características tiene según su opinión “buen” código o código limpio?</strong>

*	Que exista un estándar de nombramiento en los objetos involucrados en las distintas capas.
*	Que las variables estén nombradas con palabras dicientes relacionadas al contenido/acción que realicen; Y en camelCase.
*	Si se captura una excepción, se debe realizar alguna acción con dicha excepción y no solo imprimir el stacktrace.
*	Un uso adecuado de comentarios. (Dicientes y enfocados al negocio). 
*	Los comentarios no deben ser extensos, y sólo explicar el código en procesos complejos.
*	Uso de literales en mayúscula y creados como instancia de clase de tipo static final.
*	Clases y métodos sin exceso de líneas de código.
*	Que no existan warnings del IDE en las clases.
*	Que se tenga un estándar de identación de las líneas de código configuradas en el IDE.
*	Que todos los métodos expuestos en una interfaz se utilicen en las clases que lo implementen.
*	Que no exista código comentareado ya que genera dudas del por qué.
*	Que Se realicen validaciones de null.
*	Que las variables de clase se instancien y se obtengan mediante los métodos get.
*	En los métodos locales, declarar las variables al inicio si es fácilmente leíble donde se usa la variable. De lo contrario, crear la variable
en la línea anterior de su uso.


3.	<strong>¿Qué es un microservicio?, ventajas y desventajas de los microservicios</strong>

Es un estilo de arquitectura en la cual una aplicación compleja se desglosa funcionalmente en aplicaciones más sencillas y autónomas, permitiendo así 
que se facilite su desarrollo, mantenimiento, testeo y despliegue generando una independencia entre ellas. Dichas aplicaciones sencillas
se puede comunicar entre ellas mediante una interfaz o API, (peticiones HTTP) que pueden ser implementados con distintos lenguajes de programación.

Las ventajas y desventajas están enfocadas versus a las aplicaciones monolíticas.

### Ventajas

*	Facilita la escabilidad horizontal y vertical.
*	Simplifica el mantenimiento.
*	Permite realizar una escabilidad independiente para cada microservicio.
*	Facilita la aplicación del principio de responsabilidad única.
*	Facilita la identificación y corrección de errores los funcionales.
*	Permite desplegar de forma independiente cada microservicio sin necesidad de desplegar toda la aplicación.
*	El equipo de desarrollo se puede concentrar en la implementación o mantenimiento de una única unidad funcional. 
*	Reutilización de los microservicios con otros sistemas.

### Desventajas

*	Se necesita de una buena estrategia de integración y despliegue continuo.
*	Se necesita de una buena estrategia para monitorizar los microservicios.
*	Su configuración tiene un nivel de complejidad más alto.
*	Dependiendo de la cantidad de microservicios, podría ser complejo su gestión.
