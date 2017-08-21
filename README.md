Spring and React Testing App.
========================

Spring App (Server): 
----------------------------
La aplicación está desarrollada con SpringBoot, SpringData (In-Memory para no depender de una Base de Datos), Spring Restful.

#### Requerimientos
Java 8, Maven instalado y configurado en las variables de entorno del sistema (JAVA_HOME y MVN_HOME path).

#### Pasos para iniciar el servicio.
Ejecutar:

	1. cd /app-services
	2. mvn compile 
	3. mvn spring-boot:run

Esto levantará el servidor en localhost:8080
 
React App (Client): 
--------------------------
La aplicación está desarrollada con (react-bootstrap, material-ui). En la carpeta "app-client\build" se deja el código generado, por lo que puede subirlo directamente a un servidor web como Apache (Lamp, Xampp, Wampp).

#### Requerimientos
  NodeJS y NPM instalado y configurado en las variables de entorno del sistema (path).

#### Pasos para iniciar el servicio con Node.
Ejecutar:

	1. cd /app-client
	2. npm install
	3. npm start

Esto levantará el servidor en localhost:3000
_____ 
CORS:
--------
Para no tener problemas de request (CORS) el servidor debe estar escuchando por el puerto 8080 y el cliente por el puerto 3000. Ambos en localhost. 

De igual forma si no quiere tener problemas de este tipo durante la ejecución de las pruebas instale los plugins para deshabilitar CORS en el navegador:

1. Chrome: https://chrome.google.com/webstore/detail/allow-control-allow-origi/nlfbmbojpeacfghkpbjhddihlkkiljbi
2. Firefox: https://addons.mozilla.org/es/firefox/addon/cors-everywhere/
 
Esto se hace solo por motivos de prueba.

Downloads:
----------------
1. Java: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
2. Maven: https://maven.apache.org/
2. NodeJs: https://nodejs.org/es/
3. NPM: https://www.npmjs.com/
4. Xampp: https://www.apachefriends.org/es/index.html
