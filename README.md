## Project introduction
This is implantation of authentication and authorization with password hashing using Spring MVC, but without using Spring security.
	
## Technologies
Project is created with:
* Java version: 21.0.1
* Spring version: 6.1.2
	
## Setup
Before running this project, you need to create database in your MySQL and edit `custom-authentication\src\main\resources\application.properties`. 

## Scope of functionalities
Application has few html pages:
* home.html is available to anyone
* register.html with registration form
* login.html with form to log in registrated users
* pageForUsers.html is available to logged in users
