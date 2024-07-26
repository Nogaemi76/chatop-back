
# Chatopback

This project was generated with Spring Boot 3.3.0, 
Java 17, Maven as dependency manager & Jar for packaging
with [Spring Initializr](https://github.com/spring-io/initializr/)

<img src="https://img.shields.io/badge/Language-Java-orange.svg">

## Dependencies

**Spring Web**

Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.

**Lombok**

Java annotation library which helps to reduce boilerplate code.

**Spring Data JPA**

Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.

**MySQL Driver**

MySQL JDBC driver

**Spring Security 6**

Highly customizable authentication and access-control framework for Spring applications.

**JJWT**

Java library for creating and verifying JSON Web Tokens.

**ModelMapper** 

Java library for automating the mapping of objects to each other.

**springdoc-openapi** 

Library for automatically generating OpenAPI documentation and integrating Swagger UI for Spring Boot projects

## Installation

Clone project from GitHub repository
> `git clone` https://github.com/Nogaemi76/chatop-back.git

Go inside folder
> `cd chatop-back`

 To install the dependencies
> Run `mvn install`

To launch the project

Inside the project folder **src/main/java**, 
in the package **com.openclassrooms.chatopback**,
> Right-click on **ChatopbackApplication.java**
 
> Select **Run As** > **Java Application**

---
### MySQL
Server version: 8.0.X
>**Open the terminal** and **log into MySQL**

> ```CREATE DATABASE database_name;```

> ```USE database_name;```

> Open **script.sql** file in **folder src/main/resources/static**
> and copy content in terminal

At the project's root,
create file **env.properties** with following properties
>DB_DATABASE=

>DB_USER=

>DB_PASSWORD=

>SECURITY_JWT_SECRET_KEY=

>SECURITY_JWT_EXPIRATION_TIME=3600000 (for example)

---
### Front-end
Front-end project and installation instructions
>  https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring

Erratum in **form.component.ts**

Replace lines 69-71 
```
if(rental?.owner_id !== this.sessionService.user!.id) {
	this.router.navigate(['/rentals']);
}
```
by 
```
if (rental == undefined) {
	this.router.navigate(['/rentals/create']);
} else  if (rental?.owner_id !== this.sessionService.user!.id) {
	this.router.navigate(['/rentals']);
}
```
