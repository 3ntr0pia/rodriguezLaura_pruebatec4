# 🚀 Prueba Técnica 4 - Spring Boot

## 📋 Descripción General

Este proyecto implementa una **API REST** para la gestión de reservas de hoteles y vuelos. La aplicación permite a los clientes consultar y reservar habitaciones y vuelos, mientras que las operaciones de alta, baja y modificación (CRUD) para la gestión de estos recursos están protegidas mediante **Spring Security** y solo pueden ser realizadas por empleados autenticados.
Además tiene implementado Swagger para documentar y probar la API.
A su vez el proyecto cuenta con un archivo docker-compose.yml para levantar la base de datos MySQL y un Dockerfile para crear la imagen de la aplicación, si se desea ejecutar la aplicación en un contenedor de Docker.

## 🛠️ Prerrequisitos

    Tener instalado:
        Docker
        Docker Compose
        Maven (si deseas compilar manualmente)
        JIntelliJ IDEA (opcional, si quieres ejecutarlo sin Docker)

## ⚙️ Como ejecutar el proyecto

 ### 🚀Opción 1: Ejecutar con Docker y Docker Compose

Si quieres levantar la aplicación en contenedores Docker, sigue estos pasos:

1️⃣ Compilar el proyecto sin ejecutar los tests (evita fallos por falta de base de datos)
Ejecuta:

```
mvn clean install -DskipTests
```
2️⃣ Construir la imagen Docker de la aplicación
```
docker build -t pruebatecnica4 .
```

3️⃣ Finalmente ejecutamos docker-compose 
```
docker-compose up -d
```
4️⃣ Si no se crean las inserciones en la base de datos de manera automatica, ir al archivo data.sql que se encuentra en resources.
Copiar y pegar las sentencias sql para tener los datos de prueba.

## 💻 Tecnologías Utilizadas

- **Spring Boot** 
- **Spring Data JPA** 
- **Spring Security** 
- **MySQL** 
- **JUnit 5 & Mockito** 
## ⚙️ Validaciones Implementadas

- **Altas:**
    - Se evita la creación de registros duplicados (por ejemplo, hotel o reserva con la misma clave).

- **Bajas y Modificaciones:**
    - Se comprueba la existencia del registro antes de la operación.
    - Antes de eliminar un hotel o vuelo, se valida que no existan reservas asociadas.

- **Reservas:**
    - Se verifican condiciones para evitar duplicidad de reservas y se calcula correctamente el monto total en función de los parámetros de entrada.

## 📦 Instrucciones para Postman

He adjuntado un archivo de colección de **Postman** con ejemplos de uso de los endpoints implementados. Puedes importarlo y probar las funcionalidades de la API.
Cada petición esta creada para atacar a los datos de prueba que he añadido en la base de datos.
