# Challenge Literalura Application

Bienvenido al repositorio de Challenge Literalura Application, un proyecto basado en Java y Spring Boot diseñado para gestionar información sobre libros, autores e idiomas. Este README proporcionará una descripción general del proyecto, cómo configurarlo y cómo utilizar las funcionalidades principales.

## Tabla de Contenidos

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Instalación y Configuración](#instalación-y-configuración)
- [Uso de la Aplicación](#uso-de-la-aplicación)
    - [Menú Principal](#menú-principal)
    - [Funciones Disponibles](#funciones-disponibles)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)

## Descripción del Proyecto

Challenge Literalura Application es una aplicación de línea de comandos que permite gestionar y consultar información relacionada con libros, autores e idiomas. La aplicación se conecta a una API externa para obtener datos y los almacena en una base de datos local mediante JPA (Java Persistence API).

## Tecnologías Utilizadas

- Java 11
- Spring Boot
- JPA (Java Persistence API)
- Hibernate
- H2 Database (Base de datos en memoria)
- HttpClient (para el consumo de la API externa)

## Instalación y Configuración

1. **Clonar el Repositorio:**

   ```bash
   git clone https://github.com/tu-usuario/challenge-literalura.git
   cd challenge-literalura

2. **Configurar la Base de Datos:**
   La aplicación está configurada para usar una base de datos H2 en memoria. No se requiere configuración adicional para el desarrollo local.

3. **Construir y Ejecutar la Aplicación:**
   
    ```bash
   ./mvnw spring-boot:run

## Uso de la Aplicación

**Menú Principal**

1. Búsqueda de libro por título 
2. Lista de todos los libros registrados 
3. Lista de autores registrados 
4. Listar autores vivos en determinado año 
5. Exhibir cantidad de libros en un determinado idioma 
6. Ver estadísticas de descargas 
7. Ver Top 10 de libros más descargados 
8. Buscar datos de un autor por nombre 
9. Buscar autores nacidos a partir del año indicado
0.  Salir

**Funciones Disponibles**

1. ***Búsqueda de libro por título:***
Busca y guarda libros desde la API externa por título.

2. ***Lista de todos los libros registrados:***
Muestra una lista de todos los libros almacenados en la base de datos.

3. ***Lista de autores registrados:***
Muestra una lista de todos los autores almacenados en la base de datos.

4. ***Listar autores vivos en determinado año:***
Muestra una lista de autores que estaban vivos en un año específico.

5. ***Exhibir cantidad de libros en un determinado idioma:***
Muestra la cantidad de libros registrados en un idioma específico.

6. ***Ver estadísticas de descargas:***
Muestra estadísticas de descargas de libros, incluyendo la media, máximo, y mínimo.

7. ***Ver Top 10 de libros más descargados:***
Muestra los 10 libros más descargados junto con sus autores.

8. ***Buscar datos de un autor por nombre:***
Busc

9. ***Buscar autores nacidos a partir del año indicado:***
Muestra una lista de autores nacidos a partir de un año específico.

10. ***Salir:***
Salir de la aplicación

## Contribuciones 💡


¡Las contribuciones son bienvenidas! Si deseas contribuir, por favor abre un issue o envía un pull request.

## Licencia 📄
Este proyecto está bajo la Licencia de RS.

¡Gracias por usar Challenge Literalura Application!

# challenge-literalura-java-main
